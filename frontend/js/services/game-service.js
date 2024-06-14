/**
 * Service qui permet de créer un nouveau jeu avec l'API
 */
export class GameService {
    /**
     * Create a new game
     * @returns {Promise<string>}
     */
    static async createGame() {
        const response = await fetch('http://127.0.0.1:8080/api/game/create', {method: 'POST'});

        if (response.status === 200) {
            const data = await response.json();
            return data['gameCode'];
        } else {
            throw new Error('Failed to create game');
        }
    }

    /**
     * Update the score of a game
     * @param {string} gameCode
     * @param {string} label
     * @param {number} wordToGuess
     * @returns {Promise<number>}
     */
    static async updateGameScore(gameCode, label, wordToGuess) {
        const response = await fetch(`http://127.0.0.1:8080/api/score/update/${gameCode}/${label}/${wordToGuess}`, {method: 'POST'});
        if (response.status === 200) {
            const data = await response.json();
            return data["score"];
        } else {
            throw new Error('Failed to update game score');
        }
    }
    /**
     * Get the score of a game
     * @param {string} gameCode
     * @param score
     * @returns {Promise<number>}
     */
    static async sendScore(gameCode, score) {
        const response = await fetch(`http://127.0.0.1:8080/sse/channelId/${gameCode}/${score}`, {method: 'POST'});
        if (response.status === 200) {
            console.log("Score envoyer avec succès");
        } else {
            throw new Error('Failed to send score');
        }
    }
}
