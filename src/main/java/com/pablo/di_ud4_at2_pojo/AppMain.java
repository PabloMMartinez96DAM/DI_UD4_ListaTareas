package com.pablo.di_ud4_at2_pojo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pablo.di_ud4_at2_pojo.models.TaskModel;
import com.pablo.di_ud4_at2_pojo.singletons.DbConnection;
import de.jensd.fx.glyphs.icons525.Icons525;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppMain extends Application {


    //Nombre del fichero properties que almacena los datos necesarios para la cadena de conexión
    private static final String FILE_CONFIG = "settings.properties";

    //Clase que almacenará los datos del fichero settings.properties
    private static Properties config = new Properties();



    @Override
    public void start(Stage stage) throws IOException {

        //Cargamos la vista princial que es un BorderPane
        FXMLLoader mainViewLoader = new FXMLLoader(AppMain.class.getResource("views/mainView.fxml"));
        BorderPane root = mainViewLoader.load();


        //Cargamos la vista que irá en el centro del BorderPane, en este caso será la vista "createTaskView"
        FXMLLoader createViewLoader = new FXMLLoader(AppMain.class.getResource("views/createTaskView.fxml"));
        Parent centerLayout = createViewLoader.load();

        //Asignamos la vista secundaria a la principal
        root.setCenter(centerLayout);

        stage.setTitle("Vista principal!");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {

        //Cargamos el fichero de configuración
        loadConfig(config,FILE_CONFIG);

        //Obtenemos las propiedades del fichero
        String connectionString = config.getProperty("MONGODB_URI");
        String mongoDB = config.getProperty("MONGODB_DATABASE");

        //Obtenemos la instancia del singleton que se encarga de operar con la base de datos
        DbConnection dbConnection = DbConnection.getInstance();

        if(dbConnection.initializeMongoDBConnection(connectionString,mongoDB)){
            System.out.println("Nos hemos conectado satisfactoriamente con la base de datos MongoDB");
        }

        launch();
    }

    private static void loadConfig(Properties config, String fileConfig) {

        InputStream input = AppMain.class.getClassLoader().getResourceAsStream(FILE_CONFIG);
        try {
            config.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}