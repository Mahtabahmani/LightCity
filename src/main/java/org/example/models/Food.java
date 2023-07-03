package org.example.models;

public class Food extends Product{

    private final float water;
    private final float food;

    public Food(float food,float water) {
        super("food",1,4);
        this.food = food;
        this.water = water;
    }

    public float getWater() {
        return water;
    }

    public float getFood() {
        return food;
    }
}
