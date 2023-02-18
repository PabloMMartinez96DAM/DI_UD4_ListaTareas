package com.pablo.di_ud4_at2_pojo.singletonExplanation;

public class Explanation {


    public void Test(){


        ModeloUsuario model1 = new ModeloUsuario(1,"Modelo 1");
        int contadorUsuarios = ModeloUsuario.getContadorUsuarios();

        ModeloUsuario model2 = new ModeloUsuario(2,"Modelo 2");
        contadorUsuarios = ModeloUsuario.getContadorUsuarios();




        Datos singleton1 = Datos.getInstance();
        singleton1.setModelId(model1.getId());


        Datos singleton2 = Datos.getInstance();
        singleton2.getModelId();


    }
}
