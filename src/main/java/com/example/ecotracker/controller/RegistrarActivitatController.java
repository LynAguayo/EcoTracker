package com.example.ecotracker.controller;

import com.example.ecotracker.dao.ActivitatDAO;
import com.example.ecotracker.model.Activitat;
import com.example.ecotracker.util.CO2Calculator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RegistrarActivitatController {
    /**
     * Connectar el codi amb els elements visuals del fitxer .fxml
     */
    @FXML private TextField nameField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextArea descriptionArea;
    @FXML private TextField valueField;
    @FXML private Button backButton;

    /**
     * Controlar el comportament de la finestra principal de JavaFX
     */
    private Stage primaryStage;

}
