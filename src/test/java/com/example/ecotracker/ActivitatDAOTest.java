package com.example.ecotracker;

import com.example.ecotracker.dao.ActivitatDAO;
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

    @Test
    void testFindAll() throws SQLException {
        try (MockedStatic<DBConnector> mocked = Mockito.mockStatic(DBConnector.class)) {
            mocked.when(DBConnector::getConnection).thenReturn(mockConnection);

            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

            // Simulem 2 files a ResultSet i després el final
            when(mockResultSet.next()).thenReturn(true, true, false);

            // Valors per a cada fila (simulem dues activitats)
            when(mockResultSet.getInt("id")).thenReturn(1, 2);
            when(mockResultSet.getString("name")).thenReturn("Bicicleta a la feina", "Teletreball");
            when(mockResultSet.getDate("date")).thenReturn(
                    Date.valueOf(LocalDate.of(2025, 1, 10)),
                    Date.valueOf(LocalDate.of(2025, 1, 25))
            );
            when(mockResultSet.getString("category")).thenReturn("Transport", "Teletreball");
            when(mockResultSet.getString("description")).thenReturn(
                    "Vaig anar en bicicleta 10 km a la feina",
                    "Vaig treballar des de casa tot el dia"
            );
            when(mockResultSet.getDouble("co2_saved")).thenReturn(2.4, 1.8);

            // Obtenim la llista d'activitats
            List<Activitat> activitats = activitatDAO.findAll();

            // Comprovem que hem obtingut dues activitats
            assertEquals(2, activitats.size());

            // Verifiquem els camps de la primera activitat
            Activitat first = activitats.get(0);
            assertEquals("Bicicleta a la feina", first.getName());
            assertEquals(LocalDate.of(2025, 1, 10), first.getDate());

            // Verifiquem els camps de la segona activitat
            Activitat second = activitats.get(1);
            assertEquals("Teletreball", second.getName());
            assertEquals(LocalDate.of(2025, 1, 25), second.getDate());
        }
    }

    /**
     * Test que comprova el càlcul total de CO2 estalviat a partir de la base de dades.
     */
    @Test
    void testGetTotalCo2Saved() throws SQLException {
        try (MockedStatic<DBConnector> mocked = Mockito.mockStatic(DBConnector.class)) {
            // Simulem connexió a la base de dades
            mocked.when(DBConnector::getConnection).thenReturn(mockConnection);

            // Simulem l'execució de la consulta SQL que retorna la suma total
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);

            // Simulem que el resultat és 4.2
            when(mockResultSet.getDouble("total")).thenReturn(4.2);

            // Obtenim el total cridant el DAO
            double total = activitatDAO.getTotalCo2Saved();

            // Comprovem que el total és correcte (amb tolerància 0.001)
            assertEquals(4.2, total, 0.001);
        }
    }

    @Test
    void testDelete() throws SQLException {
        try (MockedStatic<DBConnector> mocked = Mockito.mockStatic(DBConnector.class)) {
            mocked.when(DBConnector::getConnection).thenReturn(mockConnection);

            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            // Intentem eliminar una activitat amb id = 1
            activitatDAO.delete(Integer.valueOf(1));

            // Verifiquem que es va preparar i executar la sentència amb el paràmetre correcte
            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).executeUpdate();
        }
    }
}
