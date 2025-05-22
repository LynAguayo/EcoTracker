package com.example.ecotracker.util;

public class CO2Calculator {
    // Factors de conversió per calcular el CO2 estalviat segons la categoria
    private static final double TRANSPORT_FACTOR = 0.24; // kg CO2 per km recorregut
    private static final double TELETREBALL_FACTOR = 1.8; // kg CO2 per dia teletreballat
    private static final double RECICLATGE_FACTOR = 1.0; // kg CO2 per kg de material reciclat
    private static final double ENERGIA_FACTOR = 0.15; // kg CO2 per hora amb aparells apagats
    private static final double CONSUM_LOCAL_FACTOR = 0.5; // kg CO2 per producte local comprat
    private static final double REUTILITZACIO_FACTOR = 0.4; // kg CO2 per ús d'objectes reutilitzats

    // Calcula el CO2 estalviat segons la categoria de l'activitat i el valor introduït
    public static double calculateCO2(String category, double value) {
        switch (category) {
            case "Transport":
                return value * TRANSPORT_FACTOR;
            case "Teletreball":
                return value * TELETREBALL_FACTOR;
            case "Reciclatge":
                return value * RECICLATGE_FACTOR;
            case "Energia":
                return value * ENERGIA_FACTOR;
            case "Consum local":
                return value * CONSUM_LOCAL_FACTOR;
            case "Reutilització":
                return value * REUTILITZACIO_FACTOR;
            case "Altres":
                return value; // per "altres", s'usa el valor directament
            default:
                return 0.0;
        }
    }

    // Retorna un text d'ajuda personalitzat per a cada categoria
    public static String getInputPrompt(String category) {
        switch (category) {
            case "Transport":
                return "¿Quants km has recorregut?";
            case "Teletreball":
                return "¿Quants dies has teletreballat?";
            case "Reciclatge":
                return "¿Quants kg has reciclat?";
            case "Energia":
                return "¿Quantes hores ho has mantingut apagat?";
            case "Consum local":
                return "¿Quants productes locals has comprat?";
            case "Reutilització":
                return "¿Quantes vegades has reutilitzat alguna cosa?";
            case "Altres":
                return "Introdueix el valor de CO2 estalviat (kg):";
            default:
                return "Introdueix un valor:";
        }
    }
}