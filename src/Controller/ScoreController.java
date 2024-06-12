package Controller;

import DAO.GameDAO;
import DAO.IncludeDAO;
import DAO.WordDAO;
import models.WordColor;
import webserver.WebServerContext;

import java.util.HashMap;
import java.util.Map;


/**
 * Contrôleur pour la gestion des scores
 */
public class ScoreController {
    private final static Map<int, int> gameBlueCardCountMap = new HashMap<int, int>();
    private final static Map<int, int> gameCurrentScoreMap = new HashMap<int, int>();
    private final static Map<int, int> gameTurnScoreMap = new HashMap<int, int>();

    /**
     * Mettre à jour le score du jeu
     *
     * @param context
     */
    public static void updateGameScore(WebServerContext context) {
        GameDAO gameDAO = new GameDAO();
        WordDAO wordDAO = new WordDAO();
        IncludeDAO includeDAO = new IncludeDAO();

        // Récupérer le code de la partie, le label du mot et le nombre de mots à deviner à partir de la requête HTTP
        String gameCode = context.getRequest().getParam("gameCode");
        String label = context.getRequest().getParam("label");
        int wordsToGuess = Integer.parseInt(context.getRequest().getParam("wordsToGuess"));

        int gameId = gameDAO.findGameByCode(gameCode).id();

        // Initialiser les maps de la game si ce n'était pas le cas
        gameBlueCardCountMap.putIfAbsent(gameId, 0);
        gameCurrentScoreMap.putIfAbsent(gameId, 0);
        gameTurnScoreMap.putIfAbsent(gameId, 0);

        int wordId = wordDAO.findWordByLabel(label).id();
        WordColor color = includeDAO.getInclude(gameId, wordId).color();
        switch (color) {
            case BLUE:
                int oldBlueCardCount = gameBlueCardCountMap.get(gameId);
                int newBlueCardCount = ++oldBlueCardCount;
                gameBlueCardCountMap.put(gameId, newBlueCardCount);

                int oldTurnScore = gameTurnScoreMap.get(gameId);
                int newTurnScore = oldTurnScore;
                boolean isLastBlueCard = newBlueCardCount == wordsToGuess + 1;

                // Calculer le nouveau score
                if (newBlueCardCount <= wordsToGuess) {
                    newTurnScore = oldTurnScore + newBlueCardCount;
                } else if (isLastBlueCard) {
                    newTurnScore = oldTurnScore + (wordsToGuess + 1) * (wordsToGuess + 1);
                }

                // Mettre à jour le score
                gameTurnScoreMap.put(gameId, newTurnScore);
                updateGameScore(gameId, newTurnScore, gameDAO);

                // Passer au prochain tour si c'est la dernière carte bleue à deviner
                if (isLastBlueCard) {
                    nextTurn(gameId);
                }

                context.getResponse().json(Map.of("status", GameStatus.IN_PROGRESS.name()));
                break;
            case GREY:
                nextTurn(gameId);
                context.getResponse().json(Map.of("status", GameStatus.IN_PROGRESS.name()));
                break;
            case BLACK:
                gameCurrentScoreMap.put(gameId, 0);
                gameDAO.updateScore(0, gameId);
                context.getResponse().json(Map.of("status", GameStatus.OVER.name()));
                break;
        }
    }

    /**
     * Mettre à jour le score du jeu
     *
     * @param gameId identifiant de la partie
     * @param newTurnScore nouveau score du tour
     * @param gameDAO DAO du jeu
     */
    private static void updateGameScore(int gameId, int newTurnScore, GameDAO gameDAO) {
        int oldScore = gameCurrentScoreMap.get(gameId);
        int newScore = oldScore + newTurnScore;
        gameDAO.updateScore(newScore, gameId);
        gameCurrentScoreMap.put(gameId, newScore);
    }

    /**
     * Passe au tour suivant et réinitialise les scores et les compteurs
     *
     * @param gameId identifiant de la partie
     */
    private static void nextTurn(int gameId) {
        gameTurnScoreMap.put(gameId, 0);
        gameBlueCardCountMap.put(gameId, 0);
    }


    //TODO à supprimer après
    /**
     * Envoie le score du jeu
     *
     * @param context
     */
    public static void sendGameScore(WebServerContext context) {
        GameDAO gameDAO = new GameDAO();
        String gameCode = context.getRequest().getParam("gameCode");
        int score = gameDAO.findGameByCode(gameCode).score();
        context.getResponse().json(score);
    }

    /**
     * Etat de la partie
     */
    private enum GameStatus {
        IN_PROGRESS, OVER
    }

}
