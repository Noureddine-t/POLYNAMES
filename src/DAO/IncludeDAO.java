package DAO;

import database.PolyNamesDatabase;
import models.Include;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncludeDAO {
    private PolyNamesDatabase db;

    public IncludeDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    public void createInclude(Include include) {
        String query = "INSERT INTO include (word_id, game_id, color) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, include.word_id());
            preparedStatement.setInt(2, include.game_id());
            preparedStatement.setString(3, include.color());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'instance Include : " + e.getMessage());
        }
    }

    public Include readInclude(int id) {
        Include include = null;
        String query = "SELECT * FROM include WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                include = new Include(
                        resultSet.getInt("id"),
                        resultSet.getString  ("color"),
                        resultSet.getInt("game_id"),
                        resultSet.getInt("word_id")
                        );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture de l'instance Include : " + e.getMessage());
        }
        return include;
    }
    public void updateColor(int id, String newColor) {
        String query = "UPDATE include SET color = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, newColor);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la couleur de l'instance Include : " + e.getMessage());
        }
    }


}