package com.pablo.di_ud4_at2_pojo.controllers;

import com.pablo.di_ud4_at2_pojo.models.enums.TaskState;
import com.pablo.di_ud4_at2_pojo.singletons.DbConnection;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CreateTaskViewController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button exitBtn;

    @FXML
    private ToggleButton finishedTglBtn;

    @FXML
    private ToggleButton nonStartedTglBtn;

    @FXML
    private Button sendBtn;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private ToggleButton wipTglBtn;

    private TaskState state = TaskState.NONE;


    private ToggleGroup tglGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Se establecen valores por defecto para los componentes
        this.datePicker.setValue(LocalDate.now());
        this.descriptionTextField.setPromptText("Introduce una descripción de la tarea a realizar");
        this.state = TaskState.NON_STARTED;

        //Implementación de los ToggleBtn
        this.tglGroup = new ToggleGroup();
        List<ToggleButton> btns = Arrays.asList(this.nonStartedTglBtn,this.wipTglBtn,this.finishedTglBtn);

        for(int i = 0; i< btns.size();i++){
            ToggleButton btn = btns.get(i);
            btn.setToggleGroup(this.tglGroup);
            TaskState state = TaskState.values()[i+1];
            btn.setText(state.toString());
            btn.setGraphic(new Icons525View(state.getIcon(),"2em"));
        }

        //Marcamos un ToggleButton por defecto
        this.tglGroup.selectToggle(this.nonStartedTglBtn);
    }

    @FXML
    void onSendBtnAction(ActionEvent event) {
        //Creamos un documento nuevo
        Document newDocument = new Document("_id",new ObjectId())
                .append("description",this.descriptionTextField.getText())
                .append("state", this.state)
                .append("date",this.datePicker.getValue().toString());

        //Obtenemos la instancia del singleton de conexión
        DbConnection dbConn = DbConnection.getInstance();

        //Lo añadimos al ObservableList
        dbConn.tasks.add(newDocument);

        //Subimos el documento a la base de datos
        dbConn.tasksCollection.insertOne(newDocument);

        //Mostramos una notificación para avisar al usuario que ser ha subido correctamente el documento
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Tarea guardada");
        alert.setTitle("Base de datos actualizada");
        alert.setContentText("La tarea: " +this.descriptionTextField.getText()+" se ha guardado en la base de datos");
        alert.showAndWait();

    }

    @FXML
    void onFinishedTglBtnAction(ActionEvent event) {
        state = TaskState.FINISHED;
    }

    @FXML
    void onWipTglBtnAction(ActionEvent event) {
        state = TaskState.WIP;
    }

    @FXML
    void onNonStartedTglBtnAction(ActionEvent event) {
        state = TaskState.NON_STARTED;
    }


    @FXML
    void onExitBtnAction(ActionEvent event) {
        Platform.exit();
    }


}
