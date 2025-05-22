package com.example.ecotracker;

import com.example.ecotracker.controller.GraficaController;
import com.example.ecotracker.model.Activitat;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraficaControllerTest {
    @Test
    void testCalculoCO2PorMes() {
        List<Activitat> activitats = Arrays.asList(
                // Gener 2025
                new Activitat(
                        "Bicicleta a la feina",
                        LocalDate.of(2025, 1, 10),
                        "Transport",
                        "Vaig anar en bicicleta 10 km a la feina",
                        10 * 0.24
                ),
                new Activitat(
                        "Teletreball",
                        LocalDate.of(2025, 1, 25),
                        "Teletreball",
                        "Vaig treballar des de casa tot el dia",
                        1 * 1.8
                ),

                // Febrer 2025
                new Activitat(
                        "Reciclatge setmanal",
                        LocalDate.of(2025, 2, 5),
                        "Reciclatge",
                        "Vaig reciclar 3 kg de paper i plàstic",
                        3 * 1.0
                ),
                new Activitat(
                        "Vaig apagar llums",
                        LocalDate.of(2025, 2, 18),
                        "Energia",
                        "Vaig apagar els llums innecessaris durant 2 hores",
                        2 * 0.15
                )
        );

        GraficaController controller = new GraficaController(); // El DAO no importa per aquest test
        double[] resultat = controller.calcularCO2PerMes(activitats);

        // Gener (index 0): 2.4 + 1.8 = 4.2
        assertEquals(4.2, resultat[0], 0.001);

        // Febrer (index 1): 3.0 + 0.3 = 3.3
        assertEquals(3.3, resultat[1], 0.001);

        // Comprovem que els altres mesos estan en 0.0
        for (int i = 2; i < 12; i++) {
            assertEquals(0.0, resultat[i], 0.001);
        }
    }
}
