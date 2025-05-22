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

    @Test
    void testInsert() throws SQLException {
        try (MockedStatic<DBConnector> mocked = Mockito.mockStatic(DBConnector.class)) {
            // Simulem la connexió a la base de dades
            mocked.when(DBConnector::getConnection).thenReturn(mockConnection);

            // Simulem l'execució de la sentència d'inserció
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(1);

            // Creem una activitat d'exemple
            Activitat activitat = new Activitat(
                    "Bicicleta a la feina",
                    LocalDate.of(2025, 1, 10),
                    "Transport",
                    "Vaig anar en bicicleta 10 km a la feina",
                    2.4
            );

            // Inserim l'activitat
            activitatDAO.insert(activitat);

            // Comprovem que l'ID s'ha assignat correctament
            assertEquals(Integer.valueOf(1), activitat.getId());

            // Verifiquem que els paràmetres s'han assignat correctament a la sentència
            verify(mockPreparedStatement).setString(1, "Bicicleta a la feina");
            verify(mockPreparedStatement).setDate(2, Date.valueOf(LocalDate.of(2025, 1, 10)));
            verify(mockPreparedStatement).setString(3, "Transport");
            verify(mockPreparedStatement).setString(4, "Vaig anar en bicicleta 10 km a la feina");
            verify(mockPreparedStatement).setDouble(5, 2.4);
        }
    }
