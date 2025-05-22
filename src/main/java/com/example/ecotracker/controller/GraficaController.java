package com.example.ecotracker.controller;

import com.example.ecotracker.dao.ActivitatDAO;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GraficaController {
    /**Gràfic de línia que mostra el CO₂ estalviat per mes*/
    @FXML
    private LineChart<String, Number> co2Chart;

    /**Botó per tornar al menú inicial*/
    @FXML private Button backButton;

    /**Controlar el comportament de la finestra principal de JavaFX*/
    private Stage primaryStage;

    /**Permet llegir/escriure activitats a la base de dade*/
    private final ActivitatDAO dao = new ActivitatDAO();
}
