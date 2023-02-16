package com.pablo.di_ud4_at2_pojo.singletons;

import org.bson.Document;

public class Context {

    private static Context instance;

    private Document document;

    public static Context getInstance() {

        if(instance ==null){
            instance = new Context();
        }

        return instance;
    }



    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
