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

    /**
     * Actualitza el gràfic de CO₂ amb les dades de la base de dades.
     * Agrupa les activitats per mes i suma el CO₂ estalviat.
     * A continuació, mostra aquestes dades al gràfic de línia.
     */
    private void updateChart() {
        try {
            List<Activitat> activities = dao.findAll();
            co2Char t.getData().clear();

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
