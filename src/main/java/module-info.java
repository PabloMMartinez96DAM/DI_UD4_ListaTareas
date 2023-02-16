module com.pablo.di_ud4_at2_pojo {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.iconsfivetwofive;

    
    opens com.pablo.di_ud4_at2_pojo to javafx.fxml;
    exports com.pablo.di_ud4_at2_pojo;
    exports com.pablo.di_ud4_at2_pojo.controllers;
    opens com.pablo.di_ud4_at2_pojo.controllers to javafx.fxml;
}