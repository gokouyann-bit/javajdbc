package tech.synelia;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/jeudecapitale_2";
    private static final String USER = "admin";
    private static final String PASSWORD = "secret123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}