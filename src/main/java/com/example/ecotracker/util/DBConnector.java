package com.example.ecotracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/ecotracker?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection = null;

    private DBConnector() {
        // Constructor per evitar la instanciació
    }

    // Mètode per obtenir la connexió a la bbbdd
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found.", e);
            }
        }
        return connection;
    }


}
