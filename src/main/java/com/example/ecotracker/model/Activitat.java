package com.example.ecotracker.model;

import java.time.LocalDate;

/**
 * Aquesta classe representa una activitat sostenible.
 * Cada activitat té un nom, una data, una categoria, una descripció i un valor estimat de CO2 estalviat.
 */
public class Activitat {
    private String nom;
    private LocalDate data;
    private String categoria;
    private String descripcio;
    private double valorCO2;

}
