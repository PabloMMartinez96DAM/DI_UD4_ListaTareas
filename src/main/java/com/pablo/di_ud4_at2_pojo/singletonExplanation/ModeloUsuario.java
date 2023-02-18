package com.pablo.di_ud4_at2_pojo.singletonExplanation;

public class ModeloUsuario {

    //Properties
    private int id;
    private String nombre;

    private static int contadorUsuarios = 0;




    //Constructor
    public ModeloUsuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        contadorUsuarios++;

    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static int getContadorUsuarios() {
        return contadorUsuarios;
    }
}
