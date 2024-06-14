import {RoleService} from '../services/role-service.js';

/**
 * Fonction principale pour gerer la page des roles
 * @returns {Promise<void>}
 */
async function run() {
    document.querySelector('form').addEventListener('submit', async (event) => {
        event.preventDefault();

        // Récupérer la valeur du rôle choisi
        const role = document.querySelector('input[name="role"]:checked').value;

        // Récupérer le nom d'utilisateur et le code de la partie depuis l'URL
        const params = new URLSearchParams(window.location.search);
        const username = params.get('username');
        const gameCode = params.get('gameCode');

        // Participer et obtenir le rôle depuis le serveur
        try {
            const roleName = await RoleService.participate(username, gameCode, role);

            // Redirection selon le role choisi
            const currentUrl = window.location.href;
            let nextUrl = "";
            let newUrl = "";
            const baseUrl = currentUrl.split('/roles.html')[0];
            if (role === 'maitre_des_intuitions') {
                nextUrl = baseUrl + '/intuition_master.html';
                newUrl = nextUrl + `?gameCode=${gameCode}`;
            } else if (role === 'maitre_des_mots') {
                nextUrl = baseUrl + '/words_master.html';
                newUrl = nextUrl + `?gameCode=${gameCode}`;
            } else {
                if (roleName === 'maitre_des_intuitions') {
                    nextUrl = baseUrl + '/intuition_master.html';
                    newUrl = nextUrl + `?gameCode=${gameCode}`;
                } else if (roleName === 'maitre_des_mots') {
                    nextUrl = baseUrl + '/words_master.html';
                    newUrl = nextUrl + `?gameCode=${gameCode}`;
                }
            }

            window.location.href = newUrl;
        } catch (error) {
            console.error('Erreur lors de l\'association du rôle:', error);
            alert('Échec de l\'association du rôle.');
        }
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

