/**
 * Service SSE pour envoyer l'indice et son nombre correspondant avec l'API
 */
export class HintService {
    /**
     * Send hint
     * @param gameCode
     * @param hint
     * @param number
     * @returns {Promise<void>}
     */
    static async sendHint(gameCode, hint, number) {
        const response = await fetch(`http://127.0.0.1:8080/sse/channelId/${gameCode}/${hint}/${number}`, {method: 'POST'});
        if (response.status === 200) {
            console.log("Hint envoyé avec succès");
        } else {
            throw new Error('Failed to send hint');
        }
    }
}