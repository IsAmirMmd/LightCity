package org.example.interfaces;

import org.example.models.Character;
import org.example.models.Property;

public interface MunicipalityInterface {

//    Buy and sell property
    Property buyProperty();
    void sellProperty(Property property);
    void showProperties(Character character);
}
