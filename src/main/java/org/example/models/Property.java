package org.example.models;

public class Property {
    private float[] scales;
    private float[] coordinate;
    private Character owner ;
    public int value;
    private String name;
    private boolean onSale;
    public Property(float[] scales, float[] coordinate, Character owner,String name) {
        this.name = name;
        this.scales = scales;
        this.coordinate = coordinate;
        this.owner = owner;
        this.value = 2;
        this.onSale = true;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public float[] getScales() {
        return scales;
    }

    public void setScales(float[] scales) {
        this.scales = scales;
    }

    public Character getOwner() {
        return owner;
    }

    public void setOwner(Character owner) {
        this.owner = owner;
    }

    public float[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(float[] coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public String getName() {
        return name;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }
}
