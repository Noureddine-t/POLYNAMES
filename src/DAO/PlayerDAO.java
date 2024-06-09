package DAO;

import database.PolyNamesDatabase;
import models.Game;
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
    public void createPlayer(Player player) {
        String query = "INSERT INTO player ( username, password, game_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, player.username());
            preparedStatement.setString(2, player.password());
            preparedStatement.setInt(3, player.game_id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du joueur : " + e.getMessage());
        }
    }

    public void updatePlayerGame(Player player, int gameId) {
        String query = "UPDATE player SET game_id = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, player.id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du champ game_id pour le joueur " + player.id() + " : " + e.getMessage());
        }
    }

    public void updatePlayer(Player player) {
        String query = "UPDATE player SET username = ?, password = ?, game_id= ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, player.username());
            preparedStatement.setString(2, player.password());
            preparedStatement.setInt(3, player.game_id());
            preparedStatement.setInt(4, player.id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du joueur : " + e.getMessage());
        }
    }

    //TODO implémenter méthode pour modifier et supprimer un joueur depuis son user name
    //TODO username doit etre unique
}
