package com.example.ecotracker.model;

import java.time.LocalDate;

/**
 * Aquesta classe representa una activitat sostenible.
 * Cada activitat té un nom, una data, una categoria, una descripció i un valor estimat de CO2 estalviat.
 */
public class Activitat {
    private String nom; // nom de l'activitat
    private LocalDate data; // data en què s'ha realitzat l'activitat
    private String categoria; // categoria de l'activitat
    private String descripcio; // descripció de l'activitat
    private double valorCO2; // quantitat estimada de C=2 estalviat durant l'activitat

    /**
     * Constructor per inicialitzar una activitat amb tots els seus camps.
     */
    public Activitat(String nom, LocalDate data, String categoria, String descripcio, double valorCO2) {
        this.nom = nom;
        this.data = data;
        this.categoria = categoria;
        this.descripcio = descripcio;
        this.valorCO2 = valorCO2;
    }

    /**
     * Getters per accedir a les dades de l'activitat
     */
    public String getNom() {
        return nom;
    }
    public LocalDate getData() {
        return data;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getDescripcio() {
        return descripcio;
    }
    public double getValorCO2() {
        return valorCO2;
    }

    /**
     * Setters per modificar les dades de l'activitat
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
    public void setValorCO2(double valorCO2) {
        this.valorCO2 = valorCO2;
    }
}
