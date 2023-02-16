package com.pablo.di_ud4_at2_pojo.singletonExplanation;

public class Explanation {


    public void Test(){


        Model model1 = new Model(1,"Modelo 1");
        Model model2 = new Model(2,"Modelo 2");


        Singleton singleton1 = Singleton.getInstance();
        singleton1.setModelId(model1.getId());


        Singleton singleton2 = Singleton.getInstance();
        singleton2.getModelId();


    }
}
