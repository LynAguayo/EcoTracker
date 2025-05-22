package com.example.ecotracker.controller;

import com.example.ecotracker.dao.ActivitatDAO;
import com.example.ecotracker.model.Activitat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class GraficaController {

    // Gràfic de línia que mostra el CO2 estalviat per mes
    @FXML
    private LineChart<String, Number> co2Chart;

    // Botó per tornar al menú inicial
    @FXML private Button backButton;

    // Controlar el comportament de la finestra principal
    private Stage primaryStage;

    // Permet llegir/escriure activitats a la base de dades
    private final ActivitatDAO dao = new ActivitatDAO();

    // Assigna la finestra principal
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Inicialitza el controlador i carrega les dades del gràfic
    @FXML
    public void initialize() {
        updateChart();
    }

    // Funció per tornar al menú inicial
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

    // Actualitza el gràfic de CO2 amb les dades de la base de dades.
    private void updateChart() {
        try {
            List<Activitat> activities = dao.findAll();
            co2Chart.getData().clear();

            // Agrupa les activitats per mes i suma el CO₂ estalviat
            Map<String, Double> monthlyData = activities.stream()
                    .collect(Collectors.groupingBy(
                            activity -> activity.getDate().getMonth().getDisplayName(TextStyle.FULL, new Locale("ca")),
                            Collectors.summingDouble(Activitat::getCo2Saved)
                    ));

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("CO₂ estalviat per mes");

            monthlyData.forEach((month, co2) ->
                    series.getData().add(new XYChart.Data<>(month, co2))
            );

            co2Chart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
