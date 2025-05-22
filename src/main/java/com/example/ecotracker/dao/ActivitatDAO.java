package com.example.ecotracker.dao;

import com.example.ecotracker.model.Activitat;
import com.example.ecotracker.util.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivitatDAO {
    // Crea la taula sustainable_activities si no existeix a la bbdd
    public void createTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS sustainable_activities (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(255) NOT NULL,
                date DATE NOT NULL,
                category VARCHAR(100) NOT NULL,
                description TEXT,
                co2_saved DOUBLE NOT NULL
            )
        """;

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Insereix una nova activitat sostenible a la bbdd
    public void insert(Activitat activitat) throws SQLException {
        String sql = "INSERT INTO sustainable_activities (name, date, category, description, co2_saved) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, activitat.getName());
            pstmt.setDate(2, Date.valueOf(activitat.getDate()));
            pstmt.setString(3, activitat.getCategory());
            pstmt.setString(4, activitat.getDescription());
            pstmt.setDouble(5, activitat.getCo2Saved());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    activitat.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Recupera totes les activitats sostenibles ordenades per data descendent
    public List<Activitat> findAll() throws SQLException {
        List<Activitat> activities = new ArrayList<>();
        String sql = "SELECT * FROM sustainable_activities ORDER BY date DESC";

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Activitat activitat = new Activitat();
                activitat.setId(rs.getInt("id"));
                activitat.setName(rs.getString("name"));
                activitat.setDate(rs.getDate("date").toLocalDate());
                activitat.setCategory(rs.getString("category"));
                activitat.setDescription(rs.getString("description"));
                activitat.setCo2Saved(rs.getDouble("co2_saved"));
                activities.add(activitat);
            }
        }

        return activities;
    }

    // Elimina una activitat de la bbdd segons la ID
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM sustainable_activities WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // Calcula el total de CO2 estalviat amb totes les activitats
    public double getTotalCo2Saved() throws SQLException {
        String sql = "SELECT SUM(co2_saved) as total FROM sustainable_activities";

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("total");
            }
        }

        return 0.0;
    }
}