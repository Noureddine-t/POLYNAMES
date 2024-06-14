package Controller;

import DAO.GameDAO;
import DAO.PlayerDAO;
import webserver.WebServerContext;

/**
 * Contrôleur pour la gestion des joueurs
 */
public class PlayerController {

    /**
     * Créer un joueur à partir de son nom
     *
     * @param context
     */
    public static void createPlayer(WebServerContext context) {
        PlayerDAO playerDAO = new PlayerDAO();

        // Créer un joueur à partir de son nom
        String name = context.getRequest().getParam("username");
        String gameCode = context.getRequest().getParam("gameCode");
        int gameId = new GameDAO().findGameByCode(gameCode).id();
        playerDAO.createPlayer(name, gameId);

        context.getResponse().ok("Player " + name + " created successfully");
    }


}
