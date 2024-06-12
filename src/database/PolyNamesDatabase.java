package database;

import java.sql.SQLException;

public class PolyNamesDatabase extends MySQLDatabase {
    private static final String URL = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String DATABASE_NAME = "poly_names";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public PolyNamesDatabase() throws SQLException {
        super(URL, PORT, DATABASE_NAME, USER, PASSWORD);
    }
}
