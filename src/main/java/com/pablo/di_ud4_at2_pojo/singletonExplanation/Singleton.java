package com.pablo.di_ud4_at2_pojo.singletonExplanation;

public final class Singleton {

    //Properties
    private static Singleton instance;

    private int modelId;

    public static Singleton getInstance() {
        if(instance == null){
            instance = new Singleton();
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
