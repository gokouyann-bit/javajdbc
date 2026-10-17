package tech.synelia;

import java.sql.*;

public class ScoreDAO {
    private final Connection conn;

    public ScoreDAO(Connection conn) {
        this.conn = conn;
    }

    public void enregistrer(int utilisateurId, int bonnes, int mauvaises) throws SQLException {
        String sql = "INSERT INTO score (bonnes_reponses, mauvaises_reponses, utilisateur_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bonnes);
            ps.setInt(2, mauvaises);
            ps.setInt(3, utilisateurId);
            ps.executeUpdate();
        }
    }
}