package Controller;

import DAO.GameDAO;
import DAO.IncludeDAO;
import DAO.WordDAO;
import models.Game;
import models.Word;
import models.WordColor;
import webserver.WebServerContext;
import webserver.WebServerResponse;

import java.util.*;

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
        // Créer une liste de couleurs en fonction des règles (15 gris, 2 noirs, 8 bleus)
        List<WordColor> colors = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            colors.add(WordColor.GREY);
        }
        for (int i = 0; i < 2; i++) {
            colors.add(WordColor.BLACK);
        }
        for (int i = 0; i < 8; i++) {
            colors.add(WordColor.BLUE);
        }

        // Mélanger les couleurs
        Collections.shuffle(colors);
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            WordColor color = colors.get(i);
            includeDAO.createInclude(color, currentGame.id(), word.id());
        }
    }
    /**
     * Envoie d'un indice
     *
     * @param context
     */
    public static void addHint(WebServerContext context) {
        WebServerResponse response = context.getResponse();
        String gameCode = context.getRequest().getParam("gameCode");
        String hint = context.getRequest().getParam("hint");
        int number = Integer.parseInt(context.getRequest().getParam("number"));

        // Envoi de l'indice via SSE dans le canal de la partie
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("hint", hint);
        responseMap.put("number", String.valueOf(number));
        context.getSSE().emit(gameCode, responseMap);
        response.ok("Envoie effectué avec succès");
    }
}
