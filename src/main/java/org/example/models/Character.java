package org.example.models;

import org.example.Menu;
import org.example.defualtSystem.Life;
import org.example.defualtSystem.Municipality;
import org.example.interfaces.CharacterInterface;

import java.util.ArrayList;
import java.util.Scanner;

public class Character implements CharacterInterface {
    private User userInfo;
    private BankAccount account;
    private Life life;
    private Job job;
    public ArrayList<Property> properties;
    private ArrayList<Stock> stocks;
    private Property inPosition;
    public ArrayList<Product> products;

    public Character(User userInfo, BankAccount account, Life life, Job job, ArrayList<Property> properties, Property inPosition) {
        this.userInfo = userInfo;
        this.account = account;
        this.life = life;
        this.job = job;
        this.properties = properties;
        this.inPosition = inPosition;
        this.stocks = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void gotToLocation(Property destination){
        if(destination==null)return;
        inPosition = destination;
    }
    public Property getInPosition(){
        return inPosition;
    }
    public ArrayList<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;

    }
    public ArrayList<Stock> getStocks() {
        return stocks;
    }
    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public void positionProcessing(Municipality municipality) {

        Scanner scanner = new Scanner(System.in);

        boolean check = true;
        while (check){
            String choice = scanner.next();
            Menu.propertyMenu(inPosition);
            switch (choice){
                case "a":
                    if(inPosition.isOnSale()){
                        System.out.println("This Property is located at ("+inPosition.getCoordinate()[0]+","+inPosition.getCoordinate()[1]+").");
                        System.out.println("The Price is "+inPosition.getValue());
                        System.out.println("Are you sure you want to buy this property ? yes/no");
                        String answer = scanner.nextLine().trim().toLowerCase();
                        if(answer.equals("yes")){
                            if(municipality.buyProperty(this,inPosition)){
                                System.out.println("money transferred successfully,You are now the owner of this land. ");
                            }else {
                                System.out.println("Sorry,Looks like something went wrong.");
                            }
                        }
                    }else {
                        System.out.println("Sorry,this property is not on sale.");
                    }
                    break;
                case "b":
                    municipality.showBusiness(inPosition,this);
                    break;
                case "c":
                    check = false;
                    break;
            }
        }
    }
    public void showAssets(){
        if(properties.size()>0){
            System.out.println("Properties:");
            System.out.println("You have "+properties.size()+" land with the value of "+properties.get(0).getValue()+"$ each.");
            System.out.println("the names and coordinate of the lands are :");
            for (int i=0;i<properties.size();i++){
                System.out.println(properties.get(i).getName()+" at ("+properties.get(i).getCoordinate()[0]+","+properties.get(i).getCoordinate()[1]+")");
            }
        }
        if (stocks.size()>0){
            System.out.println("Stocks :");
            for (int i=0;i<stocks.size();i++){
                System.out.println(stocks.get(i).getTitle()+"with the value of "+stocks.get(i).getValue()+"$");
            }
        }
        System.out.println("You have "+getAccount().getMoney()+"$ in your bank account.");

    }
    public void dashboard(Municipality municipality){
        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        while (check){
            System.out.println("                       Dashboard");
            System.out.println("----------------------------------------------------------");
            System.out.println("1.Find Job\n2.Assets\n3.Creating Business\n4.Your Business\n5.View Industries Growth\n6.Back to previous menu");
            switch (scanner.nextInt()){
                case 1:
                    municipality.findJob(this);
                    break;
                case 2:
                    showAssets();
                    break;
                case 3:
                    municipality.createBusiness(this);
                    break;
                case 4:
                    municipality.manageBusiness(this);
                    break;
                case 5:
                    municipality.showBusinessChart();
                    break;
                case 6:
                    check = false;
                    break;
            }
        }

    }
    public void life(Municipality municipality){
        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        while (check){
            String choice = scanner.next();
            System.out.println("                       Life");
            System.out.println("----------------------------------------------------------");
            System.out.println("a.Life Detail\nb.Buy and Consume Food\nc.Buy and Consume Liquid\nd.Back to previous menu");
            switch (choice){
                case "a":
                    life.showLife();
                    break;
                case "b":
                    municipality.eatFood(this);
                    break;
                case "c":
                    municipality.drinkLiquid(this);
                    break;
                case "d":
                    check = false;
                    break;
            }
        }
    }
}
