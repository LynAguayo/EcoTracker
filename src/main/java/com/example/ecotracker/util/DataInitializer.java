package com.example.ecotracker.util;

import com.example.ecotracker.dao.ActivitatDAO;
import com.example.ecotracker.model.Activitat;

import java.time.LocalDate;
import java.util.List;

public class DataInitializer {
    private final ActivitatDAO activitatDAO;

    // Constructor
    public DataInitializer() {
        this.activitatDAO = new ActivitatDAO();
    }

    // Inicialitza les dades de prova si no n'hi ha cap a la bbdd
    public void initializeData() {
        try {
            // Comprovar si la taula esta buida
            List<Activitat> existingActivities = activitatDAO.findAll();
            if (existingActivities.isEmpty()) {
                insertTestData();
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Insereix activitats de prova a la bbdd
    private void insertTestData() throws Exception {
        // Enero 2025
        insertActivity(
                "Bicicleta a la feina",
                LocalDate.of(2025, 1, 10),
                "Transport",
                "Vaig anar en bicicleta 10 km a la feina",
                10 * 0.24
        );

        insertActivity(
                "Teletreball",
                LocalDate.of(2025, 1, 25),
                "Teletreball",
                "Vaig treballar des de casa tot el dia",
                1 * 1.8
        );

        // Febrero 2025
        insertActivity(
                "Reciclatge setmanal",
                LocalDate.of(2025, 2, 5),
                "Reciclatge",
                "Vaig reciclar 3 kg de paper i plàstic",
                3 * 1.0
        );

        insertActivity(
                "Vaig apagar llums",
                LocalDate.of(2025, 2, 18),
                "Energia",
                "Vaig apagar els llums innecessaris durant 2 hores",
                2 * 0.15
        );

        // Marzo 2025
        insertActivity(
                "Transport públic",
                LocalDate.of(2025, 3, 12),
                "Transport",
                "Vaig utilitzar l'autobús per a un trajecte de 5 km",
                5 * 0.24
        );

        insertActivity(
                "Teletreball",
                LocalDate.of(2025, 3, 28),
                "Teletreball",
                "Dia de treball remot",
                1 * 1.8
        );

        // Abril 2025
        insertActivity(
                "Compra local",
                LocalDate.of(2025, 4, 3),
                "Consum local",
                "Vaig comprar 4 productes d'agricultors locals",
                4 * 0.5
        );

        insertActivity(
                "Vaig reutilitzar un envàs",
                LocalDate.of(2025, 4, 20),
                "Reutilització",
                "Vaig reutilitzar un pot de vidre per tercera vegada",
                1 * 0.4
        );

        // Mayo 2025
        insertActivity(
                "Bicicleta al centre",
                LocalDate.of(2025, 5, 8),
                "Transport",
                "Vaig anar en bicicleta 8 km al centre",
                8 * 0.24
        );

        insertActivity(
                "Reciclatge de metall",
                LocalDate.of(2025, 5, 19),
                "Reciclatge",
                "Vaig portar 2 kg de llaunes a reciclar",
                2 * 1.0
        );
    }

    // Insereix una activitat concreta a la bbdd
    private void insertActivity(String name, LocalDate date, String category, String description, double co2Saved) throws Exception {
        Activitat activitat = new Activitat(name, date, category, description, co2Saved);
        activitatDAO.insert(activitat);
    }
}