package org.example.defualtSystem;

import org.example.interfaces.MunicipalityInterface;
import org.example.models.*;
import org.example.models.Character;

import java.util.ArrayList;
import java.util.Scanner;

public class Municipality implements MunicipalityInterface {
    private ArrayList<Property> properties;
    protected ArrayList<Industry> industries;
    protected ArrayList<Business> businesses;
    private FastFoodShop fastFoodShop1;
    private Starbucks starbucks1;
    public  Municipality(Character character){
        generateProperties();
        industries = new ArrayList<>();
        businesses = new ArrayList<>();
        Business fastFoodShop = new FastFoodShop(properties.get(0),character);
        properties.get(0).setOnSale(false);
        businesses.add(fastFoodShop);
        this.fastFoodShop1 = (FastFoodShop) fastFoodShop;
        Business starbucks = new Starbucks(properties.get(1),character);
        properties.get(1).setOnSale(false);
        businesses.add(starbucks);
        this.starbucks1 = (Starbucks) starbucks;
    }

    private void generateProperties() {
//        Create an algorithm for generating properties for city
        properties = new ArrayList<>();
        String streetName;
        for (int i=1;i<4;i++){
            for (int j=1;j<6;j++){
                if(i==1){
                    streetName = "rose";
                } else if(i==2){
                    streetName = "marvel";
                }else {
                    streetName = "fair";
                }
                Property property = new Property(new float[]{20, 30}, new float[]{i, j},null,streetName+j);
                properties.add(property);
            }
        }
    }

    public Property searchProperty(String name){
        for (int i=0;i<properties.size();i++){
            if(properties.get(i).getName().equals(name))
                return properties.get(i);
        }
        return null;
    }
    public void showBusiness(Property property,Character character){
        for (int i=0;i<businesses.size();i++){
            if (businesses.get(i).getName().equals(property.getName())){
                System.out.println("There is a "+businesses.get(i).getTitle()+" on this property.");
                Scanner scanner = new Scanner(System.in);
                boolean check = true;
                while (check){
                    System.out.println("Do you want to... ");
                    System.out.println("1.View the product");
                    System.out.println("2.Look For Job Opportunities");
                    System.out.println("3.Look For Investing Options");
                    System.out.println("4.Back to Previous Menu");
                    switch (scanner.nextInt()){
                        case 1:
                            businesses.get(i).showProduct(character);
                            break;
                        case 2:
                            businesses.get(i).showJobOffer(character);
                            break;
                        case 3:
                            businesses.get(i).InvestingOption(character);
                            break;
                        case 4:
                            check = false;
                            break;
                    }
                }

            }
        }
        System.out.println("There is no business located on this land.");
    }
    @Override
    public boolean buyProperty(Character character,Property property) {
        for (int i=0;i<properties.size();i++){
            if (properties.get(i).equals(property)&&properties.get(i).isOnSale()){
                if(character.getAccount().withdraw(character,properties.get(i).value)){
                    if(properties.get(i).getOwner() != null){
                        character.getAccount().deposit(properties.get(i).getOwner(),properties.get(i).value);
                        properties.get(i).getOwner().properties.remove(property);
                    }
                    properties.get(i).setOwner(character);
                    character.properties.add(property);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void sellProperty(Property property,Character owner) {
        for (int i=0;i<properties.size();i++){
            if(properties.get(i).equals(property) && properties.get(i).getOwner().equals(owner) ){
                properties.get(i).setOnSale(true);
            }
        }
    }
    public void findJob(Character character){
        System.out.println("               Job Opportunities");
        System.out.println("------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        for (int i=0;i<businesses.size();i++){
            if(businesses.get(i).isHiring()){
                businesses.get(i).showJobOffer(character);
                System.out.println("press 0 to see more job offers\npress 1 to exit");
                if(scanner.nextInt()==1)
                    break;
                else
                    continue;
            }
        }
    }

    @Override
    public void showProperties() {
        System.out.println("                All Properties Of the City");
        System.out.println("--------------------------------------------------------------\n");
        for (int i=0;i<properties.size();i++){
            float[] coordination = properties.get(i).getCoordinate();
            System.out.println(++i + ".The "+properties.get(i).getName()+" is a property located at "+coordination[0]+","+coordination[1]+".");
            System.out.println("Owner : "+properties.get(i).getOwner().getUserInfo().getUsername());
            System.out.println("Price : "+properties.get(i).getValue());
            System.out.println("Is On Sale :"+properties.get(i).isOnSale());
            for (int j=0;j<businesses.size();j++){
                if(businesses.get(j).getCoordinate().equals(coordination)){
                    System.out.println("The "+businesses.get(j).getTitle()+" business is located on this land.");
                }
            }
            --i;
            System.out.println("");
        }
    }
    public void showBusinessChart(){
        BarChartEx barChartEx = new BarChartEx(this.businesses);
    }
    public void createBusiness(Character character){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the land you want to create your business in:");
        String propertyName = scanner.next().toLowerCase().trim();
        System.out.println("Enter your business name :");
        String title = scanner.next().toLowerCase().trim();
        System.out.println("Enter name of the product you are gonna sell:");
        String productName = scanner.next().toLowerCase().trim();
        System.out.println("How much does it costs to produce this product?");
        float pPrice = scanner.nextFloat();
        System.out.println("How much do you want to sell it for?");
        float cPrice = scanner.nextFloat();
        for(int i=0;i<properties.size();i++){
            if(properties.get(i).getName().equals(propertyName)){
                if(properties.get(i).getOwner().equals(character)){
                    Product product = new Product(productName,pPrice,cPrice);
                    Business business = new Business(product,title,properties.get(i),character);
                    System.out.println("Congrats!You created a Business.For more information check out 'Your Business' section.");
                }else
                    System.out.println("You don't own this property ,therefor you can't create business on it.");
            }
        }
    }

    public void manageBusiness(Character character){
        boolean isOwner = false;
        for(int i=0;i<businesses.size();i++){
            if(businesses.get(i).getOwner().equals(character)){
                Scanner scanner = new Scanner(System.in);
                boolean check = true;
                while (check){
                    System.out.println("Do you want to... ");
                    System.out.println("1.See Business Detail");
                    System.out.println("2.Enter Stock Market");
                    System.out.println("3.Stock Go Public");
                    System.out.println("4.Show Stock Price Chart");
                    System.out.println("5.Hiring Employee");
                    System.out.println("6.View Industries Growth");
                    System.out.println("7.Back to Previous Menu");
                    switch (scanner.nextInt()){
                        case 1:
                            businesses.get(i).showBusinessDetail();
                            break;
                        case 2:
                            businesses.get(i).enterStockMarket();
                            break;
                        case 3:
                            businesses.get(i).stocksGoPublic();
                            break;
                        case 4:
                            businesses.get(i).showStockChart();
                            break;
                        case 5:
                            businesses.get(i).employeeNumber();
                            break;
                        case 6:
                            showBusinessChart();
                            break;
                        case 7:
                            check = false;
                            break;
                    }
                }
                isOwner = true;
            }
        }
        if(!isOwner){
            System.out.println("Sorry,You don't own any Business.");
        }
    }
    public void eatFood(Character character){
        fastFoodShop1.showProduct(character);
    }
    public void drinkLiquid(Character character){
        starbucks1.showProduct(character);
    }
}
