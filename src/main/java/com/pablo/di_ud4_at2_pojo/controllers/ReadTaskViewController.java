package com.pablo.di_ud4_at2_pojo.controllers;

import com.pablo.di_ud4_at2_pojo.AppMain;
import com.pablo.di_ud4_at2_pojo.models.enums.TaskState;
import com.pablo.di_ud4_at2_pojo.singletons.DbConnection;
import de.jensd.fx.glyphs.icons525.Icons525;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ReadTaskViewController implements Initializable {

    @FXML
    private TableColumn<Document, String> dateCol;

    @FXML
    private TableColumn<Document, String> descriptCol;

    @FXML
    private TableColumn<Document, Icons525View> stateCol;

    @FXML
    private TableView<Document> tasksTableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dateCol.setCellValueFactory( param -> {

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

            Icons525View iconView = new Icons525View(state.getIcon(),"2em");
            return new SimpleObjectProperty<>(iconView);
        });

        //Ontenemos la referencia con el singleton manejador de la base de datos
        DbConnection dbConn = DbConnection.getInstance();

        //Añadimos la lista al tableview
        this.tasksTableView.setItems(dbConn.tasks);

    }


}
