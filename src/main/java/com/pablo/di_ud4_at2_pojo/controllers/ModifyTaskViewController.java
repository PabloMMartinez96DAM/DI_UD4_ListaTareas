package com.pablo.di_ud4_at2_pojo.controllers;

import com.pablo.di_ud4_at2_pojo.models.enums.TaskState;
import com.pablo.di_ud4_at2_pojo.singletons.Context;
import com.pablo.di_ud4_at2_pojo.singletons.DbConnection;
import de.jensd.fx.glyphs.icons525.Icons525View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.mongodb.client.model.Filters.eq;

public class ModifyTaskViewController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private Button exitBtn;

    @FXML
    private ToggleButton finishedTglBtn;

    @FXML
    private Button modifyBtn;

    @FXML
    private ToggleButton nonStartedTglBtn;

    @FXML
    private ToggleButton wipTglBtn;

    TaskState state = TaskState.NONE;
    private ToggleGroup tglGroup;

    private Context ctx;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Obtenemos el documento seleccionado en la tabla
        ctx = Context.getInstance();
        Document document = ctx.getDocument();


        this.datePicker.setValue(LocalDate.parse(document.getString("date")));
        this.descriptionTextField.setText(document.getString("description"));


        this.state = TaskState.valueOf(document.getString("state"));

        //Implementación de los ToggleBtn
        this.tglGroup = new ToggleGroup();
        List<ToggleButton> btns = Arrays.asList(this.nonStartedTglBtn, this.wipTglBtn, this.finishedTglBtn);

        for (int i = 0; i < btns.size(); i++) {
            ToggleButton btn = btns.get(i);
            btn.setToggleGroup(this.tglGroup);
            TaskState state = TaskState.values()[i + 1];
            btn.setText(state.toString());
            btn.setGraphic(new Icons525View(state.getIcon(), "2em"));
        }

        switch (state) {
            case WIP -> this.tglGroup.selectToggle(this.wipTglBtn);
            case NON_STARTED -> this.tglGroup.selectToggle(this.nonStartedTglBtn);
            case FINISHED -> this.tglGroup.selectToggle(this.finishedTglBtn);
        }


        //Marcamos un ToggleButton por defecto
        this.tglGroup.selectToggle(this.nonStartedTglBtn);
    }

    @FXML
    void onExitBtnAction(ActionEvent event) {
        Stage currentStage = (Stage) this.datePicker.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void onFinishedTglBtnAction(ActionEvent event) {
        this.state = TaskState.FINISHED;
    }

    //Botón para modificar
    @FXML
    void onModifyBtnAction(ActionEvent event) {

        Document oldDocument = ctx.getDocument();

        Document newDocument = new Document()
                .append("description", this.descriptionTextField.getText())
                .append("state", this.state)
                .append("date", this.datePicker.getValue().toString());

        //Filtros para la inserción de datos
        Bson query = eq("_id", oldDocument.get("_id"));

        //Obtenemos la instancia del singleton de conexión
        DbConnection dbConn = DbConnection.getInstance();

        //Actualizamos el documento en la base de datos
        dbConn.tasksCollection.replaceOne(query, newDocument);

        //Actualizamos el Observable list con tremenda chapuza
        dbConn.tasksCollection.find().into(dbConn.tasks);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Elemento modificado");
        alert.setHeaderText("!Enhorabuena¡ El elemento se ha modificado¡");
        alert.setContentText("El elemento: " + newDocument.getString("description"));
    }

    @FXML
    void onNonStartedTglBtnAction(ActionEvent event) {
        this.state = TaskState.NON_STARTED;
    }

    @FXML
    void onWipTglBtnAction(ActionEvent event) {
        this.state = TaskState.WIP;
    }


}