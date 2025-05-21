package com.example.ecotracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/ecotracker?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection = null;
}
