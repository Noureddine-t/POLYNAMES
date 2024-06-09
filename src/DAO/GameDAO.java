package DAO;

import database.PolyNamesDatabase;
import models.Game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GameDAO {
    private PolyNamesDatabase db;

    public GameDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }


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

    public int findGameIdByCode(String code) {
        String query = "SELECT id FROM game WHERE code = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'ID par code " + code + ":" + e.getMessage());
        }
        return -1;
    }

    public String findCodeByGameId(int gameId) {
        String query = "SELECT code FROM game WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("code");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du code par ID " + gameId + ":" + e.getMessage());
        }
        return null;
    }


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
