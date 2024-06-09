package DAO;

import database.PolyNamesDatabase;
import models.Game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDAO {
    private PolyNamesDatabase db;

    public GameDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
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
    //TODO comment generer le code unique ?
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

    public void createGame(Game game) {
        String query = "INSERT INTO game (code, score) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, game.code());
            preparedStatement.setInt(2, game.score());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du jeu : " + e.getMessage());
        }
    }



    public void updateGame(Game game) {
        String query = "UPDATE game SET code = ?, score = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, game.code());
            preparedStatement.setInt(2, game.score());
            preparedStatement.setInt(3, game.id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du jeu : " + e.getMessage());
        }
    }

    public void deleteGame(int gameId) {
        String query = "DELETE FROM game WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, gameId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du jeu : " + e.getMessage());
        }
    }
}
