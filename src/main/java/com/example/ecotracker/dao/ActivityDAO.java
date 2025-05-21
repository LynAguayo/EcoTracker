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
}
