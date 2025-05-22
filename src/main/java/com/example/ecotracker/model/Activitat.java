package com.example.ecotracker.model;

import java.time.LocalDate;

/**
 * Aquesta classe representa una activitat sostenible.
 * Cada activitat té un id, un nom, una data, una categoria, una descripció i un valor estimat de CO2 estalviat.
 */
public class Activitat {
    /*
    * Atributs
    * */
    private Long id;
    private String name;
    private LocalDate date;
    private String category;
    private String description;
    private double co2Saved;

    public Activitat() {
    }

    /**
     * Constructor per inicialitzar una activitat amb tots els seus camps.
     */
    public Activitat(String name, LocalDate date, String category, String description, double co2Saved) {
        this.name = name;
        this.date = date;
        this.category = category;
        this.description = description;
        this.co2Saved = co2Saved;
    }

    /**
     * Getters i setters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCo2Saved() {
        return co2Saved;
    }

    public void setCo2Saved(double co2Saved) {
        this.co2Saved = co2Saved;
    }

    @Override
    public String toString() {
        return "SustainableActivity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", co2Saved=" + co2Saved +
                '}';
    }
}
