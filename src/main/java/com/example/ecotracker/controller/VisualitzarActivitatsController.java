package com.example.ecotracker.controller;

import com.example.ecotracker.dao.ActivitatDAO;
import com.example.ecotracker.model.Activitat;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisualitzarActivitatsController {
    // Referència a l'etiqueta de text per mostrar el total de CO2 estalviat
    @FXML private Text totalCo2Text;

    // Taula que mostra les activitats registrades
    @FXML private TableView<Activitat> activitiesTable;

    // Columnes de la taula per mostrar els atributs de les activitats
    @FXML private TableColumn<Activitat, String> nameColumn;
    @FXML private TableColumn<Activitat, LocalDate> dateColumn;
    @FXML private TableColumn<Activitat, String> categoryColumn;
    @FXML private TableColumn<Activitat, String> descriptionColumn;
    @FXML private TableColumn<Activitat, Double> co2Column;
    @FXML private TableColumn<Activitat, Void> actionsColumn;
    @FXML private Button backButton;

    private Stage primaryStage;
    private final ActivitatDAO dao = new ActivitatDAO();
    private final ObservableList<Activitat> activities = FXCollections.observableArrayList();

    // Assigna la finestra principal
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Inicialitza la taula, les seves columnes i carrega les dades inicials
    @FXML
    public void initialize() {
        // Initialize table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        co2Column.setCellValueFactory(new PropertyValueFactory<>("co2Saved"));

        // Add delete button to actions column
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Eliminar");
            {
                deleteButton.setOnAction(event -> {
                    Activitat activitat = getTableView().getItems().get(getIndex());
                    handleDeleteActivity(activitat);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        // Set table data
        activitiesTable.setItems(activities);

        // Load initial data
        refreshData();
    }

    // Gestiona l'exportació de les activitats a un fitxer CSV
    @FXML
    private void handleExportCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar a CSV");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File file = fileChooser.showSaveDialog(activitiesTable.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write header
                writer.write("Nom,Data,Categoria,Descripció,CO₂ estalviat (kg)\n");

                // Write data
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (Activitat activitat : activities) {
                    writer.write(String.format("%s,%s,%s,%s,%.2f\n",
                            activitat.getName(),
                            activitat.getDate().format(formatter),
                            activitat.getCategory(),
                            activitat.getDescription(),
                            activitat.getCo2Saved()
                    ));
                }
                showAlert("Èxit", "Dades exportades correctament.");
            } catch (IOException e) {
                showAlert("Error", "No s'ha pogut exportar el fitxer: " + e.getMessage());
            }
        }
    }

    // Torna al menú inicial
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

    // Elimina una activitat de la bbdd i actualitza la taula
    private void handleDeleteActivity(Activitat activitat) {
        try {
            dao.delete(activitat.getId());
            refreshData();
            showAlert("Èxit", "Activitat eliminada correctament.");
        } catch (SQLException e) {
            showAlert("Error", "No s'ha pogut eliminar l'activitat: " + e.getMessage());
        }
    }

    // Recàrrega les dades de la bbdd i actualitza la taula i el total de CO2
    private void refreshData() {
        // Run database operations in a background thread
        new Thread(() -> {
            try {
                List<Activitat> allActivities = dao.findAll();
                double total = dao.getTotalCo2Saved();

                // Update UI in the JavaFX Application Thread
                Platform.runLater(() -> {
                    activities.setAll(allActivities);
                    totalCo2Text.setText(String.format("%.2f kg", total));
                    activitiesTable.refresh(); // Force table refresh
                });
            } catch (SQLException e) {
                Platform.runLater(() -> {
                    showAlert("Error", "No s'han pogut carregar les activitats: " + e.getMessage());
                    totalCo2Text.setText("Error");
                });
            }
        }).start();
    }

    // Mostra un missatge d'alerta tipus informació a l'usuari
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}