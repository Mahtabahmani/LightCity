package org.example.interfaces;

import org.example.models.Character;
import org.example.models.Property;

public interface MunicipalityInterface {

//    Buy and sell property
    boolean buyProperty(Character character,Property property);
    void sellProperty(Property property,Character character);
    void showProperties();
}
