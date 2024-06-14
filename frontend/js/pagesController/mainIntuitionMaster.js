import {WordsService} from '../services/words-service.js';
import {SSEClient} from "../libs/sse-client.js";
import {GameService} from "../services/game-service.js";

/**
 * Fonction principale pour la page du maître des intuitions
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

        // Ajouter un événement de clic pour afficher la couleur et mettre à jour le score
        const handleClick = async () => {
            let pickColorToCSS = (color) => {
                if (color === "grey")
                    return "rgba(113, 114, 113, 0.8)";
                if (color === "blue")
                    return "rgba(39, 113, 245, 0.8)";
                if (color === "black")
                    return "rgba(36, 39, 43, 0.8)";
            };
            gridItem.style.backgroundColor = pickColorToCSS(wordObj.color.toLowerCase());

            const number = document.getElementById('number').textContent;
            const score = await GameService.updateGameScore(gameCode, wordObj.label, parseInt(number, 10));
            document.getElementById('score').textContent = score.toString();
            //evoie du score lors du clic
            await GameService.sendScore(gameCode,score);
            // Retirer le gestionnaire d'événements après le premier clic
            gridItem.removeEventListener('click', handleClick);
        };
        gridItem.addEventListener('click', handleClick);
        gridContainer.appendChild(gridItem);
    });

    // Connexion SSE: reception d'indice et sa valeur
    let client = new SSEClient("localhost:8080");
    await client.connect();
    await client.subscribe(gameCode, (event) => {
        let hint = event["hint"];
        let number = event["number"];
        document.getElementById('hint').textContent = hint;
        document.getElementById('number').textContent = number;
    });

}

window.addEventListener("load", async (event) => {
    await run();
});