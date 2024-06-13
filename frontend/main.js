import { GameService } from './services/game-service.js';

import { SSEClient } from "./libs/sse-client.js";

async function run(){
    let client = new SSEClient("127.0.0.1:26790");
    client.connect();

}

window.addEventListener("load", async (event) => {await run();});


