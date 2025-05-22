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

    /**
     * Gestiona l'acció de registrar una nova activitat quan l'usuari fa clic al botó "Afegir"
     * Valida que els camps no estiguin buits
     * Calcula el Co2 estalviat amb la categoria i valor introduït
     * Desa l'activitat a la base de dades
     * Mostra un missatge de confirmació o d'error
     */
    @FXML
    private void handleAddActivity() {
        try {
            String name = nameField.getText();
            var date = datePicker.getValue();
            String category = categoryComboBox.getValue();
            String description = descriptionArea.getText();
            double value = Double.parseDouble(valueField.getText());

            // Comprovació de camps obligatoris
            if (name == null || name.trim().isEmpty() || date == null || category == null) {
                showAlert("Error", "Si us plau, omple tots els camps obligatoris.");
                return;
            }

            // Calcula el Co2 estalviat segons la categoria
            double co2Saved = CO2Calculator.calculateCO2(category, value);

            // Crea l'objecte i el desa a la base de dades
            Activitat activitat = new Activitat(name, date, category, description, co2Saved);
            dao.insert(activitat);

            // Neteja el formulari
            clearForm();

            // Missatge d'èxit
            showAlert("Èxit", "Activitat registrada correctament.");
        } catch (NumberFormatException e) {
            showAlert("Error", "El valor ha de ser un número vàlid.");
        } catch (SQLException e) {
            showAlert("Error", "No s'ha pogut guardar l'activitat: " + e.getMessage());
        }
    }

    /**
     * Torna a la pantalla del menú principal
     * Carrega el FXML corresponent
     * Passa el Stage al controlador del menú inicial
     * Mostra l'escena del menú
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

}
