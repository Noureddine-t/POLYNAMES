package DAO;

import database.PolyNamesDatabase;
import models.Include;
import models.Word;

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

   //TODO



}
