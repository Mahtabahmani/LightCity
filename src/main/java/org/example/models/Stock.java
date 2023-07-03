package org.example.models;

public class Stock {
    private String title;
    private Business business;
    private long id;
    private float value;
    private Character owner;
    private boolean available;
    public Stock(String title,Business business){
        this.title = title;
        this.business = business;
        this.owner = business.getOwner();
        this.value =0;
        this.available = false;
        this.id = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public Business getBusiness() {
        return business;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Character getOwner() {
        return owner;
    }

    public void setOwner(Character owner) {
        this.owner = owner;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
