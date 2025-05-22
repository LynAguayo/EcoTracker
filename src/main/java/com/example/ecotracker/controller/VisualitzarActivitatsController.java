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

