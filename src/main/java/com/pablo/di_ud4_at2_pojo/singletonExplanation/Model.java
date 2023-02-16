package com.pablo.di_ud4_at2_pojo.singletonExplanation;

public class Model {

    //Properties
    private int id;
    private String nombre;


    //Constructor
    public Model(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;


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
}
