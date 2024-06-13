import Controller.*;
import models.Role;
import webserver.WebServer;

import java.io.IOException;

/**
 * Classe principale de l'application
 */
public class Main {
    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer();
        server.listen(8080);

        // Endpoint pour créer une partie
        server.getRouter().post("/game/create", GameController::createGame);

        // Endpoint pour associer un joueur à un rôle
        server.getRouter().post("/participate", ParticipateController::associatePlayerToRole);

        // Endpoint pour créer un joueur
        server.getRouter().post("/player/create", PlayerController::createPlayer);

        // Endpoint pour mettre à jour le score du jeu
        server.getRouter().post("/score/update", ScoreController::updateGameScore);

        // Endpoint pour envoyer le score du jeu
        server.getRouter().get("/score/:gameCode", ScoreController::sendGameScore);

        // Endpoint pour afficher la page HTML de la grille de jeu
        server.getRouter().get("/game/:id", (context) -> {
            String gameId = context.getRequest().getParam("id");
            String role = context.getRequest().getParam("role");

            // TODO Passer par l'énumat
             Role.valueOf(role);

            if (Role.WORD_MASTER.name().equals(role)) {
                context.getResponse().htmlFile("./frontend/grid_master.html");
                context.getResponse().ok("Game ID: " + gameId);
            } else if ("INTUITION_MASTER".equals(role)) {
                context.getResponse().htmlFile("./frontend/grid_intuition.html");
                context.getResponse().ok("Game ID: " + gameId);
            } else {
                context.getResponse().notFound("Role non reconnu : " + role);
            }
        });
    }
}