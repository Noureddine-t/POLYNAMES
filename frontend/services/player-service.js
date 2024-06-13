class Player {
    #id;
    #username;
    #gameId;
    constructor(id, username, gameId) {
        this.#id = id;
        this.#username = username;
        this.#gameId = gameId;
    }
    get id() { return this.#id; }
    get username() { return this.#username; }
    get gameId() { return this.#gameId; }

}

export class PlayerService {
    static async createPlayer(username, gameCode) {
        const response = await fetch(`http://127.0.0.1:8080/player/create/${username}/${gameCode}`, {method: 'POST'});
        if (response.status===200) {
            const data = await response.json();
            return new Player(data["id"],data["username"],data["gameId"]);
        }
        else {
            throw new Error('Failed to create player');
        }
    }
}