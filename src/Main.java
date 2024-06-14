import Controller.*;
import webserver.WebServer;
import java.io.IOException;

/**
 * Classe principale de l'application
 */
public class Main {

    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer();
        server.listen(8080);

        // Définir les endpoints
        setupApiRoutes(server);
    }

    /**
     * Définir les points de terminaison de l'API utilisée par le client
     *
     * @param server instance du serveur
     */
    private static void setupApiRoutes(WebServer server) {
        // Endpoint pour créer une partie
        server.getRouter().post("/api/game/create", GameController::createGame);

        // Endpoint pour associer un joueur à un rôle
        server.getRouter().post("/api/participate/:username/:gameCode/:role", ParticipateController::associatePlayerToRole);

        // Endpoint pour créer un joueur
        server.getRouter().post("/api/player/create/:username/:gameCode", PlayerController::createPlayer);

        // Endpoint pour mettre à jour le score du jeu
        server.getRouter().post("/api/score/update/:gameCode/:label/:wordsToGuess", ScoreController::updateGameScore);

        // Endpoint pour récupérer les mots d'une partie
        server.getRouter().get("/api/words/:gameCode", IncludeController::wordAndColor);

        // Endpoint pour SSE afin d'envoyer l'indice et le nombre de mots
        server.getRouter().post("/sse/channelId/:gameCode/:hint/:number", GameController::addHint);

        //Endpoint pour SSE afin d'envoyer le score
        server.getRouter().post("/sse/channelId/:gameCode/:score", ScoreController::sendScore);
    }
}