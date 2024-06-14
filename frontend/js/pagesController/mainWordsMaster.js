import {WordsService} from '../services/words-service.js';
import {SSEClient} from "../libs/sse-client.js";
import {HintService} from "../services/hint-service.js";
import {GameService} from "../services/game-service.js";

/**
 * Fonction principale pour la page du maître des mots
 * @returns {Promise<void>}
 */
async function run() {
    const params = new URLSearchParams(window.location.search);
    const gameCode = params.get('gameCode');

    let words = await WordsService.getWords(gameCode);

    const gridContainer = document.getElementById('game-grid');
    words.forEach(wordObj => {
        const gridItem = document.createElement('div');
        gridItem.className = 'grid-item';
        gridItem.textContent = wordObj.label;


        // Appliquer la couleur de fond directement
        let pickColorToCSS = (color) => {
            if (color === "grey")
                return "rgba(113, 114, 113, 0.8)";
            if (color === "blue")
                return "rgba(39, 113, 245, 0.8)";
            if (color === "black")
                return "rgba(36, 39, 43, 0.8)";
        }
        gridItem.style.backgroundColor = pickColorToCSS(wordObj.color.toLowerCase());
        gridContainer.appendChild(gridItem);
    });

    // Connexion SSE: evoie de l'indice et sa valeur
    let client = new SSEClient("localhost:8080");
    await client.connect();

    let sendHintButton = document.getElementById("send-hint");
    sendHintButton.addEventListener("click", async (event) => {
        let hint = document.getElementById("hint").value;
        let number = document.getElementById("number").value;
        await HintService.sendHint(gameCode, hint, number);
    });
    await client.subscribe(gameCode+"score", (event) => {
        let score = event["score"];
        document.getElementById('score').textContent = score;
    });


}

window.addEventListener("load", async (event) => {
    await run();
});
