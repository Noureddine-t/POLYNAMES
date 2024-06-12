package DAO;

import database.PolyNamesDatabase;
import models.Word;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO des mots
 */
public class WordDAO {
    private PolyNamesDatabase db;

    /**
     * Constructeur de la classe WordDAO
     */
    public WordDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    /**
     * Chercher un mot à partir de son identifiant
     *
     * @param nbrWords identifiant du mot cherché
     * @return Le mot correspondant à l'identifiant wordId
     */
    public List<Word> findWords(int nbrWords) {
        List<Word> words = new ArrayList<>();
        String query = "SELECT * FROM word ORDER BY RAND() LIMIT ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setInt(1, nbrWords);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Word word = new Word(
                        resultSet.getInt("id"),
                        resultSet.getString("label")
                );
                words.add(word);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des mots : " + e.getMessage());
        }
        return words;
    }

    public Word findWordByLabel(String label) {
        String query = "SELECT * FROM word WHERE label = ?";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
            preparedStatement.setString(1, label);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Word(
                        resultSet.getInt("id"),
                        resultSet.getString("label")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du mot : " + e.getMessage());
        }
        return null;
    }

}
