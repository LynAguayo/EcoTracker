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
}
