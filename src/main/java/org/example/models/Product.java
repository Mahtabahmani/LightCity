package org.example.models;

public class Product {
    private String title;
    private float productionPrice;         // cost of manufacturing
    private float consumptionPrice;       // cost of manufacturing plus interest
    public Product(String title,float productionPrice,float consumptionPrice){
        this.title = title;
        this.productionPrice = productionPrice;
        this.consumptionPrice = consumptionPrice;
    }

    public void setConsumptionPrice(float consumptionPrice) {
        this.consumptionPrice = consumptionPrice;
    }

    public void setProductionPrice(float productionPrice) {
        this.productionPrice = productionPrice;
    }

    public float getConsumptionPrice() {
        return consumptionPrice;
    }

    public float getProductionPrice() {
        return productionPrice;
    }

    public String getTitle() {
        return title;
    }
}
