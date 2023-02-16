package com.pablo.di_ud4_at2_pojo.controllers;

import com.pablo.di_ud4_at2_pojo.AppMain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainViewController {

    @FXML
    private Button createViewBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button readViewBtn;

    @FXML
    private BorderPane rootLayout;

    @FXML
    void onCreateBtnAction(ActionEvent event) {
        navigateTo("createTaskView.fxml");
    }

    @FXML
    void onreadViewBtnAction(ActionEvent event) {
        navigateTo("readTaskView.fxml");
    }

    @FXML
    void onExitBtnAction(ActionEvent event) {

        Platform.exit();

    }


    private void navigateTo(String fxmlFileName) {

        FXMLLoader createViewLoader = new FXMLLoader(AppMain.class.getResource("views/" + fxmlFileName));
        try {
            Parent centerLayout = createViewLoader.load();
            this.rootLayout.setCenter(centerLayout);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
