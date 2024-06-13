class Game {
    #id;
     #code;
     #score;
    constructor(id,code,score){
        this.#code = code;
        this.#id = id;
        this.#score = score;
    }
    get code() { return this.#code; }
    get id() { return this.#id; }
    get score() { return this.#score; }
}

export class GameService {
    static async createGame(username) {
        const response = await fetch('http://127.0.0.1:8080/game/create', {method: 'POST'});

        if (response.status===200) {
            const data = await response.json();
            return new Game(data['code'],data['id'],data['score']);
        }
        else {
            throw new Error('Failed to create game');
        }
    }
}
