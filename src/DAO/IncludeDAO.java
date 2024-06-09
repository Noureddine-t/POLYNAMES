package DAO;

import database.PolyNamesDatabase;
import models.Include;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncludeDAO {
    private PolyNamesDatabase db;

    public IncludeDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    public void createInclude(String color, int game_id, int word_id) {
        String query = "INSERT INTO include (word_id, game_id, color) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, word_id);
            preparedStatement.setInt(2, game_id);
            preparedStatement.setString(3, color);

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
                        resultSet.getString("color"),
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

    public void updateColor(int gameId, int wordId, String newColor) {
        String query = "UPDATE include SET color = ? WHERE game_id = ? AND word_id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, newColor);
            preparedStatement.setInt(2, gameId);
            preparedStatement.setInt(3, wordId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la couleur de l'instance Include : " + e.getMessage());
        }

    }

    public void updateColor(String code, String label, String newColor) {
        String getGameIdQuery = "SELECT id FROM game WHERE code = ?";
        String getWordIdQuery = "SELECT id FROM word WHERE label = ?";
        String updateColorQuery = "UPDATE include SET color = ? WHERE game_id = ? AND word_id = ?";

        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(getGameIdQuery);
            preparedStatement.setString(1, code);
            ResultSet gameResultSet = preparedStatement.executeQuery();
            if (!gameResultSet.next()) {
                System.err.println("Erreur : aucun jeu trouvé avec le code " + code);
                return;
            }
            int gameId = gameResultSet.getInt("id");

            // Obtenir word_id à partir de label
            preparedStatement = this.db.prepareStatement(getWordIdQuery);
            preparedStatement.setString(1, label);
            ResultSet wordResultSet = preparedStatement.executeQuery();
            if (!wordResultSet.next()) {
                System.err.println("Erreur : aucun mot trouvé avec le label " + label);
                return;
            }
            int wordId = wordResultSet.getInt("id");

            // Mettre à jour la couleur dans include
            preparedStatement = this.db.prepareStatement(updateColorQuery);
            preparedStatement.setString(1, newColor);
            preparedStatement.setInt(2, gameId);
            preparedStatement.setInt(3, wordId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la couleur de l'instance Include : " + e.getMessage());
        }
    }


}