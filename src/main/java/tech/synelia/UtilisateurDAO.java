package tech.synelia;

import java.sql.*;

public class UtilisateurDAO {
    private final Connection conn;

    public UtilisateurDAO(Connection conn) {
        this.conn = conn;
    }


    public Utilisateur findByEmail(String email) throws SQLException {
        String sql = "SELECT id, nom, prenom, email FROM utilisateur WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(rs.getInt("id"), rs.getString("nom"),
                            rs.getString("prenom"), rs.getString("email"));
                }
                return null;
            }
        }
    }


    public Utilisateur inscrire(String nom, String prenom, String email) throws SQLException {
        String sql = "INSERT INTO utilisateur (nom, prenom, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, email);
            ps.executeUpdate();
            try (ResultSet cles = ps.getGeneratedKeys()) {
                cles.next();
                return new Utilisateur(cles.getInt(1), nom, prenom, email);
            }
        }
    }
}