package DAO;

import database.PolyNamesDatabase;
import models.Participate;

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

    public void createParticipate(Participate participate) {
        String query = "INSERT INTO participate (role, game_id, player_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, participate.role());
            preparedStatement.setInt(2, participate.game_id());
            preparedStatement.setInt(3, participate.player_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'instance Participate : " + e.getMessage());
        }
    }
}