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

    /**
     * Permet llegir/escriure activitats a la base de dades
     */
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
     * Inicialitza els elements de la interfície quan es carrega la pantalla.
     * Omple el desplegable de categories
     * Afegeix un listener per mostrar una ajuda (prompt) segons la categoria
     * Estableix la data actual per defecte
     */
    @FXML
    public void initialize() {
        // Inicialitza el desplegable amb les categories predefinides
        categoryComboBox.setItems(FXCollections.observableArrayList(
                "Transport",
                "Teletreball",
                "Reciclatge",
                "Energia",
                "Consum local",
                "Reutilització",
                "Altres"
        ));

        // Canviar el text d'ajuda del camp de valor segons la categoria escollida
        categoryComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                valueField.setPromptText(CO2Calculator.getInputPrompt(newVal));
            }
        });

        // Posar la data actual per defecte
        datePicker.setValue(java.time.LocalDate.now());
    }


}
