package tech.synelia;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PaysDAO {
    private final Connection conn;

    public PaysDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Pays> findAll() throws SQLException {
        String sql = """
                SELECT p.id, p.nom, p.superficie, p.nombre_habitant, c.nom_capitale
                FROM pays p
                JOIN capitale c ON c.pays_id = p.id
                ORDER BY p.id, c.id
                """;
        Map<Integer, Pays> map = new LinkedHashMap<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Pays pays = map.get(id);
                if (pays == null) {
                    Double superficie = rs.getObject("superficie") == null ? null : rs.getDouble("superficie");
                    Long habitants = rs.getObject("nombre_habitant") == null ? null : rs.getLong("nombre_habitant");
                    pays = new Pays(id, rs.getString("nom"), superficie, habitants);
                    map.put(id, pays);
                }

                pays.getCapitales().add(rs.getString("nom_capitale"));
            }
        }
        return new ArrayList<>(map.values());
    }
}