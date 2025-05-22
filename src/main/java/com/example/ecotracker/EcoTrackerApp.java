package com.example.ecotracker;

import com.example.ecotracker.controller.MenuInicialController;
import com.example.ecotracker.dao.ActivitatDAO;
import com.example.ecotracker.util.DataInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EcoTrackerApp extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {
        // Inicialitza la base de dades i crea la taula si cal
        try {
            ActivitatDAO dao = new ActivitatDAO();
            dao.createTable();

            // Inicialitza les dades de prova si la taula està buida
            DataInitializer dataInitializer = new DataInitializer();
            dataInitializer.initializeData();
        } catch (SQLException e) {
            e.printStackTrace();
            // Mostra un diàleg d'error a l'usuari
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Base de Dades");
            alert.setHeaderText("No s'ha pogut inicialitzar la base de dades");
            alert.setContentText("Si us plau, verifica que:\n" +
                    "1. XAMPP està en execució\n" +
                    "2. MySQL està actiu\n" +
                    "3. Les credencials són correctes\n\n" +
                    "Error: " + e.getMessage());
            alert.showAndWait();
            // Tanca l'aplicació després de mostrar l'error
            System.exit(1);
        }

        // Carrega la interfície del menú inicial
        FXMLLoader fxmlLoader = new FXMLLoader(EcoTrackerApp.class.getResource("menu-inicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Assigna el controlador i li passa la finestra principal
        MenuInicialController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);

        // Configura i mostra la finestra principal
        stage.setTitle("EcoTracker - Seguiment d'Impacte Ambiental");
        stage.setScene(scene);
        stage.show();
    }
}
