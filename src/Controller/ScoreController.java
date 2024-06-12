package Controller;

import DAO.GameDAO;
import DAO.IncludeDAO;
import DAO.WordDAO;
import models.WordColor;
import webserver.WebServerContext;


/**
 * Contrôleur pour la gestion des scores
 */
public class ScoreController {
    private static int blueCardCount = 0;
    private static int currentScore = 0;
    private static int turnScore = 0;

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

        // Pour éviter de réinitialiser le score à chaque fois pendant le tour
        if (currentScore == 0) {
            currentScore = gameDAO.findGameById(gameId).score();
        }

        int wordId = wordDAO.findWordByLabel(label).id();
        WordColor color = includeDAO.getInclude(gameId, wordId).color();

        switch (color) {
            case BLUE:
                blueCardCount++;
                if (blueCardCount <= wordsToGuess) {
                    turnScore += blueCardCount;
                }
                if (blueCardCount == wordsToGuess + 1) {
                    turnScore += (wordsToGuess + 1) * (wordsToGuess + 1);
                    nextTurn(gameId, gameDAO);
                }
                gameDAO.updateScore(currentScore + turnScore,gameId );
                break;
            case GREY:
                gameDAO.updateScore(currentScore,gameId);
                nextTurn(gameId, gameDAO);
                break;
            case BLACK:
                currentScore = 0;
                gameDAO.updateScore(currentScore,gameId);
                nextTurn(gameId, gameDAO);
                break;
        }
        context.getResponse().ok("Game score updated successfully");
    }

    /**
     * Passe au tour suivant et réinitialise les scores et les compteurs
     *
     * @param gameId identifiant de la partie
     * @param gameDAO DAO de la partie
     */
    private static void nextTurn(int gameId, GameDAO gameDAO) {
        turnScore = 0;
        blueCardCount = 0;
        currentScore = 0;
        gameDAO.updateScore(currentScore,gameId);
    }

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

}
