package DAO;

import database.PolyNamesDatabase;
import models.Include;
import models.WordColor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO des inclusions, permettant de gérer les associations entre les parties et les mots avec leurs couleurs
 */
public class IncludeDAO {
    private PolyNamesDatabase db;

    /**
     * Constructeur de la classe IncludeDAO
     */
    public IncludeDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    /**
     * Créer une inclusion
     *
     * @param color   couleur du mot
     * @param game_id identifiant de la partie
     * @param word_id identifiant du mot
     */
    public void createInclude(WordColor color, int game_id, int word_id) {
        String query = "INSERT INTO include (word_id, game_id, color) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, word_id);
            preparedStatement.setInt(2, game_id);
            preparedStatement.setString(3, color.name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de l'instance Include : " + e.getMessage());
        }
    }


    public Include getInclude(int gameId, int wordId) {
        Include include = null;
        String query = "SELECT * FROM include WHERE game_id = ? AND word_id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, wordId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                include = new Include(
                        resultSet.getInt("id"),
                        WordColor.valueOf(resultSet.getString("color")),
                        resultSet.getInt("game_id"),
                        resultSet.getInt("word_id")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture de l'instance Include : " + e.getMessage());
        }
        return include;
    }

    /**
     * Lire une inclusion à partir de son identifiant
     *
     * @param id identifiant de l'inclusion
     * @return l'inclusion correspondant à l'identifiant id
     */
    public Include getInclude(int id) {
        Include include = null;
        String query = "SELECT * FROM include WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                include = new Include(
                        resultSet.getInt("id"),
                        WordColor.valueOf(resultSet.getString("color")),
                        resultSet.getInt("game_id"),
                        resultSet.getInt("word_id")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la lecture de l'instance Include : " + e.getMessage());
        }
        return include;
    }

    /**
     * Mettre à jour la couleur d'une inclusion
     *
     * @param id       identifiant de l'inclusion
     * @param newColor nouvelle couleur
     */
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

    /**
     * Mettre à jour la couleur d'une inclusion à partir de l'identifiant de la partie et du mot
     *
     * @param gameId   identifiant de la partie
     * @param wordId   identifiant du mot
     * @param newColor nouvelle couleur
     */
    public void updateColor(int gameId, int wordId, WordColor newColor) {
        String query = "UPDATE include SET color = ? WHERE game_id = ? AND word_id = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, newColor.name());
            preparedStatement.setInt(2, gameId);
            preparedStatement.setInt(3, wordId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la couleur de l'instance Include : " + e.getMessage());
        }

    }

    /**
     * Mettre à jour la couleur d'une inclusion à partir du code de la partie et du label du mot
     *
     * @param code     code de la partie
     * @param label    label du mot
     * @param newColor nouvelle couleur
     */
    public void updateColor(String code, String label, WordColor newColor) {
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
            preparedStatement.setString(1, newColor.name());
            preparedStatement.setInt(2, gameId);
            preparedStatement.setInt(3, wordId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la couleur de l'instance Include : " + e.getMessage());
        }
    }


}