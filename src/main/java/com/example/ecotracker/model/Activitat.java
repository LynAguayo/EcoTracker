package com.example.ecotracker.model;

import java.time.LocalDate;

/**
 * Aquesta classe representa una activitat sostenible.
 * Cada activitat té un id, un nom, una data, una categoria, una descripció i un valor estimat de CO2 estalviat.
 */
public class Activitat {
    private Long id; // id de l'activitat
    private String name; // nom de l'activitat
    private LocalDate date; // data en què s'ha realitzat l'activitat
    private String category; // categoria de l'activitat
    private String description; // descripció de l'activitat
    private double co2Saved; // quantitat estimada de C=2 estalviat durant l'activitat

    /**
     * Constructor vuit per evitar instancies
     */
    public Activitat() {

    }

    /**
     * Constructor per inicialitzar una activitat amb tots els seus camps.
     */
    public Activitat(String nom, LocalDate data, String categoria, String descripcio, double valorCO2) {
        this.name = name;
        this.date = date;
        this.category = categoria;
        this.description = descripcio;
        this.co2Saved = valorCO2;
    }

    /**
     * Getters per accedir a les dades de l'activitat
     */
    public Long getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    public double getCo2Saved() {
        return co2Saved;
    }

    /**
     * Setters per modificar les dades de l'activitat
     */
    public void setId() {
        this.id = id;
    }
    public void setNom(String nom) {
        this.name = name;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCo2Saved(double co2Saved) {
        this.co2Saved = co2Saved;
    }
}
