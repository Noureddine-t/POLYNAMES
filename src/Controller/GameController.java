package Controller;

import DAO.GameDAO;
import DAO.IncludeDAO;
import DAO.WordDAO;
import models.Game;
import models.Word;
import models.WordColor;
import webserver.WebServerContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Contrôleur pour la gestion des parties
 */
public class GameController {
    private static final int DEFAULT_WORDS_PER_GAME = 25;

    /**
     * Créer une partie
     *
     * @param context
     */
    public static void createGame(WebServerContext context) {
        GameDAO gameDAO = new GameDAO();
        // Créer la partie
        String gameCode = gameDAO.createGame();

        // Récupérer les mots qui seront utilisés dans la partie
        WordDAO wordDAO = new WordDAO();
        List<Word> words = wordDAO.findWords(DEFAULT_WORDS_PER_GAME);

        // Associer les mots à la partie en spécifiant les couleurs
        IncludeDAO includeDAO = new IncludeDAO();
        Game currentGame = gameDAO.findGameByCode(gameCode);
        associateColor(words, includeDAO, currentGame);

        // Indiquer dans la réponse HTTP le code de la partie créée
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("gameCode", gameCode);
        context.getResponse().json(responseMap);
    }

    /**
     * Associer les mots à la partie en spécifiant les couleurs
     *
     * @param words       liste des mots
     * @param includeDAO  DAO des inclusions
     * @param currentGame partie courante
     */
    private static void associateColor(List<Word> words, IncludeDAO includeDAO, Game currentGame) {
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            WordColor color;
            if (i < 15) {
                color = WordColor.GREY;
            } else if (i < 17) {
                color = WordColor.BLACK;
            } else {
                color = WordColor.BLUE;
            }
            includeDAO.createInclude(color, currentGame.id(), word.id());
        }
    }

}
