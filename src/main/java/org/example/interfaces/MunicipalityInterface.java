package org.example.interfaces;

import org.example.models.Character;
import org.example.models.Property;

public interface MunicipalityInterface {

//    Buy and sell property
    void buyProperty(Character character);
    void sellProperty(Character character);
    void showProperties();
}
