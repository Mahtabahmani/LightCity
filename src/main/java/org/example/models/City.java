package org.example.models;

import org.example.Menu;
import org.example.defualtSystem.*;
import org.example.interfaces.CityInterface;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class City implements CityInterface {
    private final ArrayList<Character> characters;
    private final Bank bankSystem;
    private final Municipality municipality;

    private final StockMarket stockMarket;

    private Character root;

    public City() {
        characters = new ArrayList<>();
        municipality = new Municipality(root);
//        Get Bank Property from municipality
        bankSystem = new Bank(new Property(new float[]{12, 32}, new float[]{42, 32}, root,"Bank"), root);
        stockMarket = new StockMarket();
        stockMarket.startMarketSimulation();
    }

    @Override
    public void joinCharacter(User userinfo) {
        BankAccount newAccount = bankSystem.newAccount(userinfo.getUsername(), userinfo.getPassword());
        Character character = new Character(userinfo, newAccount, new Life(), null, null, null);
        characters.add(character);
        beginGame(character);
    }
    public void continueCharacter(User target){
        for (int i=0;i<characters.size();i++){
            if(characters.get(i).getUserInfo().equals(target))
                beginGame(characters.get(i));
        }
    }

    @Override
    public void getCityDetail() {
        String players = Arrays.toString(characters.toArray());
    }


    /**
     * Begin Game function generate a new thread for each character ,<b > DO NOT CHANGE THIS FUNCTION STRUCTURE</b> ,
     *
     * */
    private void beginGame(Character character) {
        Thread thread = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                boolean check = true;
                while (check) {
                    System.out.println("Show Menu");
                    Menu.userMenu();
                    switch (scanner.next()) {
                        case "1" -> {
                            character.gotToLocation(municipality.searchProperty(Menu.getName()));
                            character.positionProcessing(municipality);
                            break;
                        }
                        case "2" -> {
                            character.positionProcessing(municipality);
                            break;
                        }
                        case "3" -> {
                            character.dashboard(municipality);
                            break;
                        }
                        case "4" -> {
                            character.life(municipality);
                            break;
                        }
                        case "5" -> {
                            check = false;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }
}
