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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RegistrarActivitatController {
    // Per connectar el codi amb els elements del .fxml
    @FXML private TextField nameField;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextArea descriptionArea;
    @FXML private TextField valueField;
    @FXML private Button backButton;

    // Controla el comportament de la finestra principal
    private Stage primaryStage;

    // Permet llegir/escriure activitats a la base de dades
    private final ActivitatDAO dao = new ActivitatDAO();

    // Assigna la finestra principal (Stage)
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Inicialitza els elements de la interfície i demana els attributs per el model activitat
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
        datePicker.setValue(LocalDate.now());

        // Configurar el DatePicker para validar las fechas
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate oneMonthAgo = today.minus(1, ChronoUnit.MONTHS);

                if (date != null) {
                    if (date.isAfter(today) || date.isBefore(oneMonthAgo)) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffcccc;");
                    }
                }
            }
        });
    }

    // Gestiona l'acció de registrar una nova activitat quan l'usuari fa clic al botó "Afegir"
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

            // Validació de la data
            LocalDate today = LocalDate.now();
            LocalDate oneMonthAgo = today.minus(1, ChronoUnit.MONTHS);

            if (date.isAfter(today)) {
                showAlert("Error", "No es pot registrar una activitat amb una data futura.");
                return;
            }

            if (date.isBefore(oneMonthAgo)) {
                showAlert("Error", "No es pot registrar una activitat amb més d'un mes d'antiguitat.");
                return;
            }

            // Comprovació de valor negatiu
            if (value < 0) {
                showAlert("Error", "No es permeten valors negatius.");
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

    // Torna a la pantalla del menú principal
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

    // Buida tots els camps del formulari i posa la data d'avui
    private void clearForm() {
        nameField.clear();
        datePicker.setValue(LocalDate.now());
        categoryComboBox.setValue(null);
        descriptionArea.clear();
        valueField.clear();
    }

    // Mostra una alerta d'informació amb el missatge
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
