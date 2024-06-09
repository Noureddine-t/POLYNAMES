package DAO;

import database.PolyNamesDatabase;
import models.Word;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {
    private PolyNamesDatabase db;

    public WordDAO() {
        try {
            this.db = new PolyNamesDatabase();
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données est impossible !");
        }
    }

    public List<Word> find25Word() {
        List<Word> words = new ArrayList<>();
        String query = "SELECT * FROM word ORDER BY RAND() LIMIT 25";
        try {
            PreparedStatement preparedStatement = this.db.prepareStatement(query);
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
}
