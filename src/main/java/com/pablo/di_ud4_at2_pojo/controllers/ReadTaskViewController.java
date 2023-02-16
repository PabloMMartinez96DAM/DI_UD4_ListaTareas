package com.pablo.di_ud4_at2_pojo.controllers;

import com.pablo.di_ud4_at2_pojo.AppMain;
import com.pablo.di_ud4_at2_pojo.models.enums.TaskState;
import com.pablo.di_ud4_at2_pojo.singletons.Context;
import com.pablo.di_ud4_at2_pojo.singletons.DbConnection;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.bson.Document;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.mongodb.client.model.Filters.eq;

public class ReadTaskViewController implements Initializable {

    @FXML
    private TableColumn<Document, String> dateCol;

    @FXML
    private TableColumn<Document, String> descriptCol;

    @FXML
    private TableColumn<Document, Icons525View> stateCol;

    @FXML
    private TableView<Document> tasksTableView;
    @FXML
    private Button exitBtn;

    @FXML
    private Button modifyBtn;
    @FXML
    private Button deletBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dateCol.setCellValueFactory(param -> {

            String dateValue = param.getValue().getString("date");
            return new SimpleObjectProperty<>(dateValue);

        });

        descriptCol.setCellValueFactory(param -> {

            String description = param.getValue().getString("description");
            return new SimpleStringProperty(description);

        });


        stateCol.setCellValueFactory(param -> {
            String stateValue = param.getValue().getString("state");

            TaskState state = TaskState.valueOf(stateValue);


            Icons525View iconView = new Icons525View(state.getIcon(), "2em");
            return new SimpleObjectProperty<>(iconView);
        });

        //Ontenemos la referencia con el singleton manejador de la base de datos
        DbConnection dbConn = DbConnection.getInstance();


        //Actualizamos el Observable lista
        dbConn.tasksCollection.find().into(dbConn.tasks);

        //Añadimos la lista al tableview
        this.tasksTableView.setItems(dbConn.tasks);

    }

    @FXML
    void onDeleteBtnAction(ActionEvent event) {
        Document selectedDocument = this.tasksTableView.getSelectionModel().getSelectedItem();
        DbConnection.getInstance().tasks.remove(selectedDocument);
        //TODO Delete


    }

    @FXML
    void onExitBtnAction(ActionEvent event) {

        Platform.exit();

    }

    @FXML
    void onModifyBtnAction(ActionEvent event) {


        Document selectedDocument = this.tasksTableView.getSelectionModel().getSelectedItem();

        if (selectedDocument != null) {



            Context ctx = Context.getInstance();
            ctx.setDocument(selectedDocument);

            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource("views/modifyTaskView.fxml"));

            try {

                Parent modifyLayout = loader.load();
                Stage currentStage = new Stage();
                currentStage.setTitle("Modificar");
                currentStage.setScene(new Scene(modifyLayout));
                currentStage.show();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("404 not found");
            errorAlert.setContentText("No has seleccionado nada, crack!");
            errorAlert.setHeaderText("Error al seleccionar");
            errorAlert.showAndWait();
        }
    }

}
