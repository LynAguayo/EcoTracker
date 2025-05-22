package com.example.ecotracker.controller;

import com.example.ecotracker.dao.ActivitatDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /**
     * Assigna la finestra principal (Stage) de l'aplicació a aquest controller.
     * Això permet accedir al Stage des d'altres mètodes i canvia escenes o tancar la finestra.
     *
     * @param primaryStage La finestra principal de JavaFX
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Inicialitza el controlador després de carregar-se la vista.
     * Carrega les dades del gràfic.
     */
    @FXML
    public void initialize() {
        updateChart();
    }

    /**
     * Gestor de l'acció del botó per tornar al menú inicial.
     * Canvia l'escena a la vista principal del menú.
     */
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ecotracker/menu-inicial.fxml"));
            Parent root = loader.load();
            MenuInicialController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EcoTracker - Menú Principal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateChart() {}


}
