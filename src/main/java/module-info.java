module com.example.ecotracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires mysql.connector.java;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    // Obrir paquets per FXML
    opens com.example.ecotracker to javafx.fxml;
    opens com.example.ecotracker.controller to javafx.fxml; // importante
    opens com.example.ecotracker.model to javafx.base;

    // Exporta els paquets que necessiten ser accedits des d'altres mòduls
    exports com.example.ecotracker;
    exports com.example.ecotracker.model;
    exports com.example.ecotracker.controller;
}