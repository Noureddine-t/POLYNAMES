package DAO;

import database.PolyNamesDatabase;
import models.Participate;
import models.Role;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO des participations, permettant de gérer les rôles des joueurs dans les parties
 */
public class ParticipateDAO {
    private PolyNamesDatabase db;

    /**
     * Constructeur de la classe ParticipateDAO
     */
    public ParticipateDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    /**
     * Créer une participation
     *
     * @param role      rôle du joueur
     * @param game_id   identifiant de la partie
     * @param player_id identifiant du joueur
     */
    public void createParticipate(Role role, int game_id, int player_id) {
        String query = "INSERT INTO PARTICIPATE (role, game_id, player_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, role.name());
            preparedStatement.setInt(2, game_id);
            preparedStatement.setInt(3, player_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'instance Participate : " + e.getMessage());
        }
    }

    /**
     * Lire une participation à partir de son identifiant
     *
     * @param id identifiant de la participation
     * @return la participation correspondant à l'identifiant id
     */
    public Participate getParticipate(int id) {
        String query = "SELECT * FROM PARTICIPATE WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Participate(
                        resultSet.getInt("id"),
                        Role.valueOf(resultSet.getString("role")),
                        resultSet.getInt("game_id"),
                        resultSet.getInt("player_id")
                );

            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture du role depuis d'ID : " + e.getMessage());
        }
        return null;
    }

    /**
     * Lire une participation à partir de l'identifiant du jeu et du joueur
     *
     * @param gameId   identifiant de la partie
     * @param playerId identifiant du joueur
     * @return la participation correspondant à l'identifiant du jeu et du joueur
     */
    public Participate getParticipate(int gameId, int playerId) {
        String role = null;
        String query = "SELECT * FROM PARTICIPATE WHERE game_id = ? AND player_id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Participate(
                        resultSet.getInt("id"),
                        Role.valueOf(resultSet.getString("role")),
                        resultSet.getInt("game_id"),
                        resultSet.getInt("player_id")
                );

            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture du role depuis l'ID du jeu et du joueur : " + e.getMessage());
        }
        return null;
    }
}