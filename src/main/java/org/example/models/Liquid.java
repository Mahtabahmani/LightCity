package org.example.models;

public class Liquid extends Product {
    private final float liquid;


    public Liquid(float liquid) {
        super("liquid",0.7F,3);
        this.liquid = liquid;
    }

    public float getLiquid() {
        return liquid;
    }

}
