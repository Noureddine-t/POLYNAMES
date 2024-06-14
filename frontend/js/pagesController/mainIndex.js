import {GameService} from '../services/game-service.js';
import {PlayerService} from "../services/player-service.js";

/**
 * Gérer la premiere page de l'application
 * @returns {Promise<void>}
 */
async function run() {

    // Gérer formulaire de création de partie
    document.getElementById("create-game-btn").addEventListener("click", async function() {
        let username = document.getElementById("username-create").value;

        // Désactiver le bouton après le premier clic
        this.disabled = true;

        // Création de la partie
        const gameCode = await GameService.createGame();
        document.getElementById("game-code").textContent = gameCode;

        // Création du nouveau bouton pour la redirection
        let redirectButton = document.createElement('button');
        redirectButton.textContent = 'Choisir un rôle';
        redirectButton.id = 'redirect-button';
        document.getElementById('game-code-div').appendChild(redirectButton);
        // Ajouter un gestionnaire d'événements au bouton de redirection
        redirectButton.addEventListener('click', () => {
            // Recupère l'URL actuelle
            const currentUrl = window.location.href;
            // Sépare l'URL à partir de '/index.html'
            const baseUrl = currentUrl.split('/index.html')[0];
            // Ajoute 'roles.html' à la partie précédente de l'URL
            const nextUrl = baseUrl + '/Pages/roles.html';
            const newUrl = nextUrl + `?gameCode=${gameCode}&username=${username}`;
            window.location.href = newUrl;
        });
        // Création de l'utilisateur
        await PlayerService.createPlayer(username, gameCode);
    });

    // Gérer formulaire pour rejoindre une partie
    document.getElementById("join-btn").addEventListener("click", async () => {
        let username = document.getElementById("username-join").value;
        // Création de la partie
        let gameCode = document.getElementById("game-code-join").value;
        // Recupère l'URL actuelle
        const currentUrl = window.location.href;
        // Sépare l'URL à partir de '/index.html'
        const baseUrl = currentUrl.split('/index.html')[0];
        // Ajoute 'roles.html' à la partie précédente de l'URL
        const nextUrl = baseUrl + '/Pages/roles.html';
        const newUrl = nextUrl + `?gameCode=${gameCode}&username=${username}`;
        window.location.href = newUrl;
        // Création de l'utilisateur
        await PlayerService.createPlayer(username, gameCode);
    });
    // Empêcher les formulaires d'actualiser la page
    for (let form of document.getElementsByTagName("form")) {
        form.addEventListener('submit', (event) => {
            event.preventDefault();
        });
    }
}

window.addEventListener("load", async (event) => {
    await run();
});
