package tech.synelia;

import javax.naming.ConfigurationException;
import java.sql.*;

public class ConfigDAO {
    private static final String CLE_NB_QUESTIONS = "nombre_de_question";

    private final Connection conn;

    public ConfigDAO(Connection conn) {
        this.conn = conn;
    }


    public String getValeur(String cle) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT valeur FROM configs WHERE cle = ?")) {
            ps.setString(1, cle);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("valeur") : null;
            }
        }
    }


    public int getNombreQuestions() throws SQLException, ConfigurationException {
        String valeur = getValeur(CLE_NB_QUESTIONS);

        if (valeur == null) {
            throw new ConfigurationException("La clé '" + CLE_NB_QUESTIONS
                    + "' est absente de la table configs.");
        }

        int nombre;
        try {
            nombre = Integer.parseInt(valeur.trim());
        } catch (NumberFormatException e) {
            throw new ConfigurationException("La valeur '" + valeur + "' de la clé '"
                    + CLE_NB_QUESTIONS + "' n'est pas un nombre entier.");
        }

        if (nombre < 1) {
            throw new ConfigurationException("La valeur de '" + CLE_NB_QUESTIONS
                    + "' doit être supérieure ou égale à 1 (trouvé : " + nombre + ").");
        }
        return nombre;
    }
}