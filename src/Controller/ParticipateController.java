package Controller;

import DAO.GameDAO;
import DAO.ParticipateDAO;
import DAO.PlayerDAO;
import models.Role;
import webserver.WebServerContext;
import java.util.*;

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
        if (roleName.equals("aleatoire")) {
            // Choisir un role arbitrairement, en excluant 'aleatoire' pour éviter de le sélectionner par hasard
            Role[] roles = Role.values();
            role = roles[new Random().nextInt(roles.length - 1)];
        } else {
            role = Role.valueOf(roleName);
        }

        int gameId = new GameDAO().findGameByCode(gameCode).id();
        int playerId = new PlayerDAO().findPlayerByUserNameAndGameId(username, gameId).id();
        participateDAO.createParticipate(role, gameId, playerId);

        // Indiquer dans la réponse HTTP le rôle assigné
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("role", role.name());
        context.getResponse().json(responseMap);
    }
}
