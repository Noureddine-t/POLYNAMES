/**
 * Service qui permet de créer un nouveau joueur avec l'API
 */
export class PlayerService {
    /**
     * Create a new player
     * @param {string} username
     * @param {string} gameCode
     * @returns {Promise<void>}
     */
    static async createPlayer(username, gameCode) {
        const response = await fetch(`http://127.0.0.1:8080/api/player/create/${username}/${gameCode}`, {method: 'POST'});
        if (response.status === 200) {
            console.log("Joueur créer avec succès");

        } else {
            throw new Error('Failed to create player');
        }
    }
}