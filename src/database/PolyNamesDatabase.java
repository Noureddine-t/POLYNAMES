package database;

import database.MySQLDatabase;

import java.sql.SQLException;

public class PolyNamesDatabase extends MySQLDatabase {
public PolyNamesDatabase() throws SQLException {
    super("127.0.0.1",3306,"poly_names","root","");
}
}
