package com.example.ecotracker.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuInicialController {
    private Stage primaryStage;

    // Estableix l'escenari principal per poder canviar d'escena
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Obre la finestra per registrar una nova activitat
    @FXML
    private void handleRegistrarActivitat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ecotracker/registrar-activitat.fxml"));
            Parent root = loader.load();
            RegistrarActivitatController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Registrar Activitat Sostenible");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Obre la finestra per visualitzar totes les activitats registrades
    @FXML
    private void handleVisualitzarActivitats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ecotracker/visualitzar-activitats.fxml"));
            Parent root = loader.load();
            VisualitzarActivitatsController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Visualitzar Activitats");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Obre la finestra amb la gràfica de les emissions de CO2 per mes
    @FXML
    private void handleVisualitzarGrafica() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ecotracker/grafica.fxml"));
            Parent root = loader.load();
            GraficaController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gràfica CO₂ per Mes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}