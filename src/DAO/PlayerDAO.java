package DAO;

import database.PolyNamesDatabase;
import models.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO des joueurs
 */
public class PlayerDAO {
    private PolyNamesDatabase db;

    /**
     * Constructeur de la classe PlayerDAO
     */
    public PlayerDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    /**
     * Créer un joueur
     *
     * @param username nom d'utilisateur du joueur
     * @param game_id  identifiant de la partie
     */
    public void createPlayer(String username, int game_id) {
        String query = "INSERT INTO player (username, game_id) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, game_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Erreur: Le nom d'utilisateur " + username + " existe déjà.");
            } else {
                System.err.println("Erreur lors de la création du joueur : " + e.getMessage());
            }
        }
    }


    /**
     * Chercher un joueur à partir de son identifiant
     *
     * @param playerId identifiant du joueur cherché
     * @return Le joueur correspondant à l'identifiant playerId
     */
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
                        resultSet.getInt("game_id"));
            }


        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du joueur par ID " + playerId + ":" + e.getMessage());
        }
        return null;
    }


    /**
     * Chercher un joueur à partir de son nom d'utilisateur
     *
     * @param username nom d'utilisateur du joueur cherché
     * @return Le joueur correspondant au nom d'utilisateur username
     */
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
                        resultSet.getInt("game_id"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du joueur par username " + username + ":" + e.getMessage());
        }
        return null;
    }

    /**
     * Chercher un joueur à partir de son nom d'utilisateur et de l'identifiant de la partie
     *
     * @param username nom d'utilisateur du joueur cherché
     * @param gameId   identifiant de la partie
     * @return Le joueur correspondant au nom d'utilisateur username et à l'identifiant de la partie gameId
     */
    public Player findPlayerByUserNameAndGameId(String username, int gameId) {
        String query = "SELECT p.id, p.username, p.game_id FROM Player p JOIN Game g ON p.game_id = g.id WHERE p.username = ? AND p.game_id = ?;";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Player(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getInt("game_id"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du joueur par username " + username + ":" + e.getMessage());
        }
        return null;
    }
}
