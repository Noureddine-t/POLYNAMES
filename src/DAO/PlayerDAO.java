package DAO;

import database.PolyNamesDatabase;
import models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDAO {
    private PolyNamesDatabase db;

    public PlayerDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    public void createPlayer(String username, String password, int game_id) {
        String query = "INSERT INTO player (username, password, game_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, game_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Erreur: Le nom d'utilisateur " + username + " existe déjà.");
            } else {
                System.err.println("Erreur lors de la création du joueur : " + e.getMessage());
            }
        }
    }


    public Player findPlayerById(int playerId) {
        String query = "SELECT * FROM player WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Player(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("game_id"));
            }


        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du joueur par ID " + playerId + ":" + e.getMessage());
        }
        return null;
    }

    public Player findPlayerByUsername(String username) {
        String query = "SELECT * FROM player WHERE username = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Player(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("game_id"));
            }


        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du joueur par username " + username + ":" + e.getMessage());
        }
        return null;
    }
}
