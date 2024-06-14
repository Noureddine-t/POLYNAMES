/**
 * Service permettant de récupérer les mots avec l'API
 */
class Words {
    #label;
    #color;

    constructor(label, color) {
        this.#label = label;
        this.#color = color;
    }

    get label() {
        return this.#label;
    }

    get color() {
        return this.#color;
    }
}

export class WordsService {
    /**
     * Get words
     * @param gameCode
     * @returns {Promise<*>}
     */
    static async getWords(gameCode) {
        const response = await fetch(`http://127.0.0.1:8080/api/words/${gameCode}`, {method: 'GET'});
        if (response.ok) {
            const data = await response.json();
            return data.map(item => new Words(item.label, item.color));
        } else {
            throw new Error('Failed to fetch words');
        }
    }
}