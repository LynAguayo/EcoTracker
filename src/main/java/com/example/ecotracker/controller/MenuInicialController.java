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