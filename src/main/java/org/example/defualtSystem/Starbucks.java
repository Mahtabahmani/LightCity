package org.example.defualtSystem;

import org.example.models.*;
import org.example.models.Character;

import java.util.Scanner;

public class Starbucks extends Business{
    public Starbucks(Property property, Character character) {
        super(new Liquid(25),"Starbucks",property,character);
    }
    @Override
    public void showJobOffer(Character character){
        if(employees.size()>=20){
            System.out.println("Sorry,We are not hiring now.");
        }else {
            System.out.println("                 Job Opportunity ");
            System.out.println("---------------------------------------------------");
            System.out.println("Business Name : "+this.getTitle());
            System.out.println("Requirements : No need");
            System.out.println("Job Title : salesman");
            System.out.println("Details : the base salary gonna be 1$ .");
            System.out.println("Do you want to sign for this job? yes/no");
            Scanner scanner = new Scanner(System.in);
            if(scanner.nextLine().toLowerCase().trim().equals("yes")){
                employment(character);
                System.out.println("Congratulation! you got hired at "+this.getTitle()+" as a salesman.good luck");
            }
        }
    }
    @Override
    public void showProduct(Character character){
        Liquid liquid = (Liquid) this.product;
        System.out.println("Starbucks sells drinks ");
        System.out.println("This drink  adds"+liquid.getLiquid()+" to your water level.");
        System.out.println("It costs "+liquid.getConsumptionPrice()+"$");
        System.out.println("Are you interested in buying? yes/no");
        Scanner scanner = new Scanner(System.in);
        if(scanner.nextLine().trim().toLowerCase().equals("yes")){
            System.out.println("How many drinks do you want ?");
            int number = scanner.nextInt();
            if(character.getAccount().withdraw(character,number*liquid.getConsumptionPrice())){
                character.getAccount().deposit(getOwner(),number*liquid.getConsumptionPrice());
                rateOfSell += number;
                for (int i=0;i<number;i++){
                    character.getLife().liquidConsumption(liquid);
                }
                System.out.println("Thank you for your purchase.");
            }else {
                System.out.println("Something went wrong, make sure you have enough money in your account");
            }
        }
    }


}
