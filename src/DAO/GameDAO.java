package DAO;

import database.PolyNamesDatabase;
import models.Game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * DAO des parties de jeu
 */
public class GameDAO {
    private PolyNamesDatabase db;

    /**
     * Constructeur de la classe GameDAO
     */
    public GameDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }


    /**
     * Créer une partie de jeu
     *
     * @return identifiant de la partie créée
     */
    public String createGame() {
        String query = "INSERT INTO game (code) VALUES (?)";

        while (true) {
            String code = UUID.randomUUID().toString();
            try {
                PreparedStatement preparedStatement = this.db.prepareStatement(query);
                preparedStatement.setString(1, code);
                preparedStatement.executeUpdate();
                return code;
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    System.err.println("Le code de jeu " + code + " existe déjà. Génération d'un nouveau code.");
                } else {
                    System.err.println("Erreur lors de la création du jeu : " + e.getMessage());
                    return null;
                }
            }
        }
    }

    /**
     * Chercher une partie de jeu à partir de son identifiant
     *
     * @param gameId identifiant de la partie cherchée
     * @return La partie de jeu correspondant à l'identifiant gameId
     */
    public Game findGameById(int gameId) {
        String query = "SELECT * FROM game WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Game(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getInt("score")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du jeu par ID " + gameId + ":" + e.getMessage());
        }
        return null;
    }

    /**
     * Chercher une partie de jeu à partir de son code
     *
     * @param code code de la partie cherchée
     * @return La partie de jeu correspondant au code
     */
    public Game findGameByCode(String code) {
        String query = "SELECT * FROM game WHERE code = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Game(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getInt("score")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du jeu par code " + code + ":" + e.getMessage());
        }
        return null;
    }

    /**
     * Mettre à jour le score d'une partie de jeu
     *
     * @param newScore nouveau score
     * @param code     code de la partie
     */
    public void updateScore(int newScore, String code) {
        String query = "UPDATE game SET score = ? WHERE code = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, newScore);
            preparedStatement.setString(2, code);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du score par le code : " + e.getMessage());
        }
    }

    /**
     * Mettre à jour le score d'une partie de jeu a partir de son identifiant
     *
     * @param newScore nouveau score
     * @param id       identifiant de la partie
     */
    public void updateScore(int newScore, int id) {
        String query = "UPDATE game SET score = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, newScore);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du score par l'ID : " + e.getMessage());
        }
    }

    /**
     * Supprimer une partie de jeu a partir de son identifiant
     *
     * @param gameId identifiant de la partie
     */
    public void deleteGame(int gameId) {
        String query = "DELETE FROM game WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, gameId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du jeu par ID : " + e.getMessage());
        }
    }

    /**
     * Supprimer une partie de jeu a partir de son code
     *
     * @param code code de la partie
     */

    public void deleteGame(String code) {
        String query = "DELETE FROM game WHERE code = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, code);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du jeu par code: " + e.getMessage());
        }
    }
}
