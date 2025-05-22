package com.example.ecotracker.util;

public class CO2Calculator {
    // Factors de conversió per calcular el CO2 estalviat segons la categoria
    private static final double TRANSPORT_FACTOR = 0.24; // kg CO2 per km recorregut
    private static final double TELETREBALL_FACTOR = 1.8; // kg CO2 per dia teletreballat
    private static final double RECICLATGE_FACTOR = 1.0; // kg CO2 per kg de material reciclat
    private static final double ENERGIA_FACTOR = 0.15; // kg CO2 per hora amb aparells apagats
    private static final double CONSUM_LOCAL_FACTOR = 0.5; // kg CO2 per producte local comprat
    private static final double REUTILITZACIO_FACTOR = 0.4; // kg CO2 per ús d'objectes reutilitzats

    /**
     * Calcula el CO2 estalviat segons la categoria de l'activitat i el valor introduït
     * @param category La categoria de l'activitat
     * @param value El valor introduït per l'usuari
     * @return CO2 estalviat en Kg
     */
    public static double calculateCO2(String category, double value) {
        return switch (category) {
            case "Transport" -> value * TRANSPORT_FACTOR;
            case "Teletreball" -> value * TELETREBALL_FACTOR;
            case "Reciclatge" -> value * RECICLATGE_FACTOR;
            case "Energia" -> value * ENERGIA_FACTOR;
            case "Consum local" -> value * CONSUM_LOCAL_FACTOR;
            case "Reutilització" -> value * REUTILITZACIO_FACTOR;
            case "Altres" -> value; // For "Altres", we use the value directly as it's manually entered
            default -> 0.0;
        };
    }

    /**
     * Retorna un text d'ajuda personalitzat per a cada categoria, que es mostra com a indicació al formulari d'entrada
     * @param category La categoria seleccionada
     * @return Text d'ajuda per guiar l'usuari
     */
    public static String getInputPrompt(String category) {
        return switch (category) {
            case "Transport" -> "¿Quants km has recorregut?";
            case "Teletreball" -> "¿Quants dies has teletreballat?";
            case "Reciclatge" -> "¿Quants kg has reciclat?";
            case "Energia" -> "¿Quantes hores ho has mantingut apagat?";
            case "Consum local" -> "¿Quants productes locals has comprat?";
            case "Reutilització" -> "¿Quantes vegades has reutilitzat alguna cosa?";
            case "Altres" -> "Introdueix el valor de CO2 estalviat (kg):";
            default -> "Introdueix un valor:";
        };
    }
}
