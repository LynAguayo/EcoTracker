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