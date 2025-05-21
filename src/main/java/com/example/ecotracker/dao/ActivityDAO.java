package com.example.ecotracker.dao;

import com.example.ecotracker.model.Activity;
import com.example.ecotracker.util.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {
    // Crea la taula sustainable_activities si no existeix a la bbdd
    public void createTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS sustainable_activities (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
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
    public void insert(Activity activity) throws SQLException {
        String sql = "INSERT INTO sustainable_activities (name, date, category, description, co2_saved) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, activity.getName());
            pstmt.setDate(2, Date.valueOf(activity.getDate()));
            pstmt.setString(3, activity.getCategory());
            pstmt.setString(4, activity.getDescription());
            pstmt.setDouble(5, activity.getCo2Saved());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    activity.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    // Recupera totes les activitats sostenibles ordenades per data descendent
    public List<Activity> findAll() throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM sustainable_activities ORDER BY date DESC";

        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Activity activity = new Activity();
                activity.setId(rs.getLong("id"));
                activity.setName(rs.getString("name"));
                activity.setDate(rs.getDate("date").toLocalDate());
                activity.setCategory(rs.getString("category"));
                activity.setDescription(rs.getString("description"));
                activity.setCo2Saved(rs.getDouble("co2_saved"));
                activities.add(activity);
            }
        }

        return activities;
    }
}
