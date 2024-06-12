package Controller;

import DAO.GameDAO;
import DAO.ParticipateDAO;
import DAO.PlayerDAO;
import models.Role;
import webserver.WebServerContext;

import java.util.Random;

/**
 * Contrôleur pour la gestion des participations
 */
public class ParticipateController {
    /**
     * Créer un joueur
     *
     * @param context
     */
    public static void associatePlayerToRole(WebServerContext context) {
        ParticipateDAO participateDAO = new ParticipateDAO();
        String username = context.getRequest().getParam("username");
        String gameCode = context.getRequest().getParam("gameCode");
        String roleName = context.getRequest().getParam("role");

        Role role;
        if (roleName == null || roleName.isEmpty()) {
            // Choir un role arbitrairement
            role = Role.values()[new Random().nextInt(Role.values().length)];
        } else {
            role = Role.valueOf(roleName);
        }

        //Crtéation de participation
        int gameId = new GameDAO().findGameByCode(gameCode).id();
        int playerId = new PlayerDAO().findPlayerByUserNameAndGameId(username, gameId).id();
        participateDAO.createParticipate(role, playerId, gameId);
        context.getResponse().ok("Player " + username + " associated to role " + role.name() + " successfully");
    }
}
