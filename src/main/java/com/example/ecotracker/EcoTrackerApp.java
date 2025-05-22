package com.example.ecotracker;

import com.example.ecotracker.controller.MenuInicialController;
import com.example.ecotracker.dao.ActivitatDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EcoTrackerApp extends Application {

    /**
     * Mètode principal que inicia l'aplicació
     * JavaFX crida el mètode start() automàticament
     */
    public static void main(String[] args) {
        launch();
    }


    /**
     * Aquest mètode s'executa quan s'inicia l'aplicació JavaFX.
     * Inicialitza la base de dades (crea la taula si no existeix)
     * Carrega la interfície gràfica del menú principal des de l'FXML
     * Assigna el controlador i mostra la finestra principal
     *
     * @param stage La finestra principa de JavaFX
     * @throws IOException Si falla la càrrega del fitxer FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Inicialitza la base de dades i crea la taula si cal
        try {
            ActivitatDAO dao = new ActivitatDAO();
            dao.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: Mostrar diàleg d'error a l'usuari si no es pot accedir a la base de dades
        }

        // Carrega la interfície del menú inicial des del fitxer FXML
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
