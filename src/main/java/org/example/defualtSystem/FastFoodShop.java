package org.example.defualtSystem;

import org.example.models.*;
import org.example.models.Character;

import java.util.Scanner;

public class FastFoodShop extends Business {
    public FastFoodShop(Property property,Character character){
        super(new Food(20,15),"Fast Food Shop",property,character);
    }
    @Override
    public void showJobOffer(Character character){
        if(employees.size()>=20){
            System.out.println("Sorry,We are not hiring now.");
        }else {
            System.out.println("                 Job Opportunity ");
            System.out.println("---------------------------------------------------");
            System.out.println("Business Name :"+this.getTitle());
            System.out.println("Requirements : No need");
            System.out.println("Job Title : salesman");
            System.out.println("Details : the base salary gonna be 1$ .");
            System.out.println("Do you want to sign for this job? yes/no");
            Scanner scanner = new Scanner(System.in);
            if(scanner.nextLine().toLowerCase().trim().equals("yes")) {
                employment(character);
                System.out.println("Congratulation! you got hired at " + this.getTitle() + " as a salesman.good luck");
            }
        }
    }
    @Override
    public void showProduct(Character character){
        Food food = (Food) this.product;
        System.out.println("This FastFoodShop business sells food ");
        System.out.println("The food  adds"+food.getFood()+" to your food level and "+food.getWater()+" to your water level.");
        System.out.println("It costs "+food.getConsumptionPrice()+"$");
        System.out.println("Are you interested in buying? yes/no");
        Scanner scanner = new Scanner(System.in);
        if(scanner.nextLine().trim().toLowerCase().equals("yes")){
            System.out.println("How many food do you want ?");
            int number = scanner.nextInt();
            if(character.getAccount().withdraw(character,number*food.getConsumptionPrice())){
                character.getAccount().deposit(getOwner(),number*food.getConsumptionPrice());
                rateOfSell += number;
                for (int i=0;i<number;i++){
                    character.getLife().foodConsumption(food);
                }
                System.out.println("Thank you for your purchase.");
            }else {
                System.out.println("Something went wrong, make sure you have enough money in your account");
            }
        }
    }

}
