package org.example.defualtSystem;

import org.example.interfaces.MunicipalityInterface;
import org.example.models.Character;
import org.example.models.Industry;
import org.example.models.Property;

import java.util.ArrayList;

public class Municipality implements MunicipalityInterface {
    private ArrayList<Property> properties;
    protected ArrayList<Industry> industries;
    public  Municipality(){
        generateProperties();
        industries = new ArrayList<>();
    }

    private void generateProperties() {
//        Create an algorithm for generating properties for city
        properties = new ArrayList<>();
        for (int i=1;i<6;i++){
            for (int j=1;j<4;j++){
                Property property = new Property(new float[]{20, 30}, new float[]{j, i},null);
                properties.add(property);
            }
        }
    }
    public Property searchProperty(int[] coordin){
        for (int i=0;i<properties.size();i++){
            if(properties.get(i).getCoordinate().equals(coordin))
                return properties.get(i);
        }
        return null;
    }
    @Override
    public void buyProperty(Character character) {
        for (int i=0;i<properties.size();i++){
            if (properties.get(i).equals(character.getInPosition())){
                if(character.getAccount().withdraw(character,properties.get(i).value)){
                    if(properties.get(i).getOwner() != null){
                        character.getAccount().deposit(properties.get(i).getOwner(),properties.get(i).value);
                    }
                    properties.get(i).setOwner(character);
                    break;
                }
            }
        }
    }

    @Override
    public void sellProperty(Character character) {
        for (int i=0;i<properties.size();i++){
            if (properties.get(i).getOwner().equals(character)){
                if (character.getAccount().deposit(character,properties.get(i).value)){
                    properties.get(i).setOwner(null);
                    break;
                }
            }
        }
    }

    @Override
    public void showProperties() {
        System.out.println("                All Properties Of the City");
        System.out.println("--------------------------------------------------------------\n");
        for (int i=0;i<properties.size();i++){
            float[] coordination = properties.get(i).getCoordinate();
            System.out.println(++i + ".This land is 600 square meters and its coordinate are"+coordination[0]+","+coordination[1]+".");
            System.out.println("The owner of this land is "+properties.get(i).getOwner().getUserInfo().getUsername());
            for (int j=0;j<industries.size();j++){
                if(industries.get(j).getCoordinate().equals(properties.get(i).getCoordinate())){
                    System.out.println("The "+industries.get(j).getTitle()+" Industry is located on this land.");
                }
            }
            --i;
            System.out.println("");
        }
    }
}
