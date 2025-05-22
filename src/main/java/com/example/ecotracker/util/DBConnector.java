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

    // Mètode per obtenir connexió amb la bbbdd
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

    // Mètode per tancar connexió amb la bbdd
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
