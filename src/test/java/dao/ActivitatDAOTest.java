package com.example.ecotracker.dao;

import com.example.ecotracker.model.Activitat;
import com.example.ecotracker.util.DBConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ActivitatDAOTest {

    // Mocks per a connexió i sentències SQL
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;
    private ActivitatDAO activitatDAO;

    @BeforeEach
    void setUp() throws SQLException {
        // Configuració prèvia dels mocks
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);
        activitatDAO = new ActivitatDAO();

        // Quan es crida a prepareStatement, que retorni el mock preparat
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
    }