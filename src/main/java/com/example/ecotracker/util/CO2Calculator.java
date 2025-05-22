package com.example.ecotracker.util;

public class CO2Calculator {
    // Factors de conversió per calcular el CO2 estalviat segons la categoria
    private static final double TRANSPORT_FACTOR = 0.24; // kg CO2 per km recorregut
    private static final double TELETREBALL_FACTOR = 1.8; // kg CO2 per dia teletreballat
    private static final double RECICLATGE_FACTOR = 1.0; // kg CO2 per kg de material reciclat
    private static final double ENERGIA_FACTOR = 0.15; // kg CO2 per hora amb aparells apagats
    private static final double CONSUM_LOCAL_FACTOR = 0.5; // kg CO2 per producte local comprat
    private static final double REUTILITZACIO_FACTOR = 0.4; // kg CO2 per ús d'objectes reutilitzats

}
