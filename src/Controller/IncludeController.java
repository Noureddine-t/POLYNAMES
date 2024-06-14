package Controller;

import DAO.GameDAO;
import DAO.IncludeDAO;
import DAO.WordDAO;
import models.Game;
import models.Include;
import webserver.WebServerContext;
import java.util.*;

/**
 * Contrôleur pour la récuperer les mots et les couleurs associées
 */
public class IncludeController {
    /**
     * Récupérer le mot et la couleur associée
     *
     * @param context
     */
    public static void wordAndColor(WebServerContext context) {
        IncludeDAO includeDAO = new IncludeDAO();
        String gameCode = context.getRequest().getParam("gameCode");
        Game game = new GameDAO().findGameByCode(gameCode);
        List<Include> includes = includeDAO.allGameIncludes(game.id());
        // List de maps des mots avec les couleurs
        List<Map<String, String>> wordList = new ArrayList<>();

        WordDAO wordDAO = new WordDAO();
        for (Include include : includes) {
            String label = wordDAO.getWord(include.word_id()).label();
            String color = include.color().name();

            Map<String, String> wordMap = new HashMap<>();
            wordMap.put("label", label);
            wordMap.put("color", color);

            wordList.add(wordMap);
        }
        context.getResponse().json(wordList);
    }
}
