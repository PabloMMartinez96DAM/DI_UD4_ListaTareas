package com.pablo.di_ud4_at2_pojo.singletons;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public final class DbConnection {

    //Properties
    private static DbConnection instance;

    public static DbConnection getInstance() {

        if(instance == null){
            instance = new DbConnection();
        }
        return instance;
    }


    //Conexión con MongoDB
    private static MongoClient mongoClient;

    //Conexión con la base de datos a utilizar
    private static MongoDatabase diUd4DB;

    //Referencia a la Collection que almacena las tareas
    private static MongoCollection<Document> tasksCollection;

    public ObservableList<Document> tasks = FXCollections.observableArrayList();



    //Método para inicializar la conexión
    public boolean initializeMongoDBConnection(String connectionString,String dbName) {

        // Valor de retorno para comprobar si se ha podido realizar la conexion
        boolean result = false;
        System.out.println("Conectando con MongoDB...");
        try {
            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));


            mongoClient = MongoClients.create(connectionString);
            diUd4DB = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
            tasksCollection = diUd4DB.getCollection("tasks");


            //Inicializamos la lista
            this.tasks = tasksCollection.find().limit(10).into(tasks);

            result = true;

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Error no se ha podido conectar a la base de datos");
            alerta.setTitle("Error no se ha podido conectar a la base de datos");
            alerta.setContentText("ERROR: No se ha podido conectar a la base de datos  " + dbName);
            alerta.showAndWait();
            System.exit(2);
        }
        return result;

    }

}
