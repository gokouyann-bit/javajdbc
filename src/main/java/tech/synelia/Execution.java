package tech.synelia;

import java.sql.Connection;
import java.sql.SQLException;

public class Execution {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            IO.println("Connecté !");
            new Menu(conn).demarrer();
        } catch (SQLException e) {
            IO.println("Erreur de connexion : " + e.getMessage());
        }
    }
}