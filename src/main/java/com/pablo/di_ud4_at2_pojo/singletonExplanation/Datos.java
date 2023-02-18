package com.pablo.di_ud4_at2_pojo.singletonExplanation;

public final class Datos {

    //Properties
    private static Datos instance;

    private int modelId;

    public static Datos getInstance() {
        if(instance == null){
            instance = new Datos();
        }
        return instance;
    }


    //Getters & Setters
    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }
}
