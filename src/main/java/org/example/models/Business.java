package org.example.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Business extends Industry {
    protected Product product;
    protected int rateOfSell;
    private float income;                    //      revenue of the company
    private float netProfit;                 //      revenue minus all the expenses
    private int employeeNum;                 //      number of employees that must be hired
    private ArrayList<Stock> stocks;
    private boolean isHiring;                 //     shows that if this business is hiring employees
    public Business(Product product,String title,Property property,Character character){
        super(title,property,character,0);
        income = 0;
        rateOfSell =0;
        netProfit = 0;
        stocks = new ArrayList<>();
        employeeNum = 0;
        isHiring = false;
        calculation();
    }

    public boolean isHiring() {
        return isHiring;
    }

    public int getEmployeeNum() {
        return employeeNum;
    }

    public void sellProduct(Character character, int number){
        System.out.println("");
        if(character.getAccount().withdraw(character,number*this.product.getConsumptionPrice())){
            if(character.getAccount().deposit(this.getOwner(),number*this.product.getConsumptionPrice())){
                rateOfSell +=number;
            }
        }
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }
    public void employeeNumber(){                          //determines how many employees need to be hired by
        System.out.println("You currently have "+employees.size()+" employee.");      //asking the owner
        System.out.println("How many employees do you want to hire?");
        Scanner scanner = new Scanner(System.in);
        isHiring = true;
        employeeNum += scanner.nextInt();
        System.out.println("Announcement has been made.");
    }
    public void showJobOffer(Character character){     //  requirements,title and details can be overridden by subclass
        if(employees.size()>=employeeNum){
            System.out.println("Sorry,We are not hiring now.");
        }else {
            System.out.println("                 Job Opportunity ");
            System.out.println("---------------------------------------------------");
            System.out.println("Business Name : "+this.getTitle());
            System.out.println("Requirements : you should own a property and have money in your bank account.");
            System.out.println("Job Title : salesman");
            System.out.println("Details : the base salary gonna be 1$ (more properties you own, more you gonna get paid).");
            System.out.println("Do you want to sign for this job? yes/no");
            Scanner scanner = new Scanner(System.in);
            if(scanner.nextLine().toLowerCase().trim().equals("yes")){
                if(character.getProperties().size()>0 &&character.getAccount().getMoney()>0){
                    employment(character);
                    System.out.println("Congratulation! you got hired at "+this.getTitle()+" as a salesman.good luck");
                }else {
                    System.out.println("Sorry,you are not qualified for this job.");
                }
            }
        }
    }
    public void employment(Character character){
        Employee employee = new Employee(character.getUserInfo().getUsername(),this,(float) 1*character.getProperties().size(),
                character.getAccount());
        employees.add(employee);
        Job job = new Job("salesman",(float) 1*character.getProperties().size());
        character.setJob(job);
    }

    public void showProduct(Character character){
        System.out.println("this business offers you "+product.getTitle()+" with just "+product.getConsumptionPrice()+"$");
        System.out.println("Are you interested in buying? yes/no");
        Scanner scanner = new Scanner(System.in);
        if(scanner.nextLine().trim().toLowerCase().equals("yes")){
            System.out.println("How many do you want ?");
            int number = scanner.nextInt();
            if(character.getAccount().withdraw(character,number*product.getConsumptionPrice())){
                character.getAccount().deposit(getOwner(),number*product.getConsumptionPrice());
                rateOfSell += number;
                character.products.add(product);
                System.out.println("thank you for your purchase");
            }

        }
    }
    public void showStock(){
        System.out.println(this.getTitle()+" Business Stock Detail");
        System.out.println("stock's name : "+stocks.get(0).getTitle());
        System.out.println("numbers of stock : "+stocks.size());
        System.out.println("price : "+stocks.get(0).getValue());

    }

    public void enterStockMarket(){
        System.out.println("                        Entering Stock Market");
        System.out.println("---------------------------------------------------------------------");
        if(stocks.size()==0){
            System.out.println("How many shares do you want to create? the number can't be changed");
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            for(int i=0;i<number;i++){
                Stock stock = new Stock(this.getTitle().toUpperCase()+"STOCK",this);
                stocks.add(stock);
                Thread thread = new Thread(()->{
                    while (true){
                        stock.setValue(netProfit/number);
                        try {
                            Thread.sleep(10800000); // wait for 1 minute
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }else {
            System.out.println("Sorry,You can't do this operation twice.");
        }

    }
    public void InvestingOption(Character character){
        System.out.println("                  Investing");
        System.out.println("---------------------------------------------------");
        if(stocks.size()==0){
            System.out.println("This business doesn't have any stocks.");
        }
        boolean check=true;
        while (check){
            System.out.println("1.See stock growth chart for the past few days ");
            System.out.println("2.See stock's details");
            System.out.println("3.Buy stock");
            System.out.println("4.Back to previous menu");
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()){
                case 1:
                    showStockChart();
                    break;
                case 2:
                    showStock();
                    break;
                case 3:
                    if(buyStock(character,buyStockNumber()))
                        System.out.println("Stocks successfully purchased");
                    else
                        System.out.println("Look like something went wrong,Try again");
                    break;
                case 4:
                    check=false;
                    break;
            }
        }
    }
    public void stocksGoPublic(){
        System.out.println("                        Stocks Going Public");
        System.out.println("--------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("You currently have "+stocks.size()+" shares of stock");
        System.out.println("How many of these shares you want to sell to public? (means can be bought by people)");
        int num = scanner.nextInt();
        for (int i=0;i<num;i++){
            stocks.get(i).setAvailable(true);
        }
    }
    public int buyStockNumber(){                //determines how many stocks are going to be bought by asking the character
        int count = 0;
        for (int i=0;i<stocks.size();i++){
            if(stocks.get(i).isAvailable()){
                count++;
            }
        }
        System.out.println("How many stocks you want to buy? (you can buy maximum"+count+")");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        return number;
    }
    public boolean buyStock(Character character,int number){
        int count=0;
        if(character.getAccount().withdraw(character,number*stocks.get(0).getValue())){
            for (int i=0;i<stocks.size();i++) {
                if (stocks.get(i).isAvailable()) {
                    if(count==number)
                        break;
                    character.getAccount().deposit(stocks.get(i).getOwner(),stocks.get(i).getValue());
                    stocks.get(i).getOwner().getStocks().remove(stocks.get(i));
                    stocks.get(i).setOwner(character);
                    character.getStocks().add(stocks.get(i));
                    stocks.get(i).setAvailable(false);
                    count++;
                }
            }
            return true;
        }
        return false;
    }
    public void showStockChart(){
    LineChartEx lineChartEx= new LineChartEx(this);
    }
    public void showBusinessDetail(){
        System.out.println("                Business Details");
        System.out.println("-----------------------------------------------------");
        System.out.println("-Product");
        System.out.println("+ Name :"+this.product.getTitle());
        System.out.println("+ Production Price: "+this.product.getProductionPrice()+"$");
        System.out.println("+ Consumption Price: "+this.product.getConsumptionPrice()+"$");
        System.out.println("-Financial Status");
        System.out.println("+ Rate Of Sale : "+this.rateOfSell);
        System.out.println("+ Total Income : "+this.income+"$");
        System.out.println("+ Profit Till This Day : "+this.netProfit+"$");
        System.out.println("-Employees ");
        for (int i=0;i<employees.size();i++){
            System.out.println("+ Username:"+employees.get(i).getUsername()+", Base Salary:"+employees.get(i).getBaseSalary()+"$ , Level:"+employees.get(i).getLevel());
        }
    }
    public void calculation(){
        Thread thread = new Thread(()->{
            while (true){
                income = rateOfSell* product.getConsumptionPrice();
                float salary=0;
                for (int i=0;i<employees.size();i++){
                    salary += employees.get(i).getBaseSalary()*employees.get(i).getLevel();
                }
                float expences = rateOfSell*product.getProductionPrice()+salary;
                netProfit = income-expences;
                try {
                    Thread.sleep(10800000); // wait for 1 minute
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


}
