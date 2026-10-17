package tech.synelia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Eleverq {
    private final Connection conn;

    public Eleverq(Connection conn) {
        this.conn = conn;
    }

    public List<Eleve> findAll() throws SQLException {
        String sql = "SELECT id, nom, prenom, matricule, classe_id FROM eleve ORDER BY id";
        List<Eleve> liste = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(new Eleve(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("matricule"),
                        rs.getInt("classe_id")));
            }
        }
        return liste;
    }

    public int ajouter(Eleve e) throws SQLException {
        String sql = "INSERT INTO eleve (nom, prenom, matricule, classe_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.nom());
            ps.setString(2, e.prenom());
            ps.setString(3, e.matricule());
            ps.setInt(4, e.classeId());
            return ps.executeUpdate();
        }
    }

    public int modifier(int id, String nom, String prenom) throws SQLException {
        String sql = "UPDATE eleve SET nom = ?, prenom = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setInt(3, id);
            return ps.executeUpdate();
        }
    }

    public int supprimer(int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM eleve WHERE id = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}