package DAO;

import database.PolyNamesDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParticipateDAO {
    private PolyNamesDatabase db;

    public ParticipateDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    public void createParticipate(String role, int game_id, int player_id) {
        String query = "INSERT INTO participate (role, game_id, player_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, role);
            preparedStatement.setInt(2, game_id);
            preparedStatement.setInt(3, player_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'instance Participate : " + e.getMessage());
        }
    }


    //provisoir
    public void updateRole(int id, String newRole) {
        String query = "UPDATE participate SET role = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, newRole);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du role de l'instance Participate : " + e.getMessage());
        }
    }

    public String getRole(int id) {
        String role = null;
        String query = "SELECT role FROM participate WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            role = preparedStatement.executeQuery().getString("role");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture du role depuis d'ID : " + e.getMessage());
        }
        return role;
    }

    public String getRole(int gameId, int playerId) {
        String role = null;
        String query = "SELECT role FROM participate WHERE game_id = ? AND player_id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, playerId);
            role = preparedStatement.executeQuery().getString("role");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture du role depuis l'ID du jeu et du joueur : " + e.getMessage());
        }
        return role;
    }
}