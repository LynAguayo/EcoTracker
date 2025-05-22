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
