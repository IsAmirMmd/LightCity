package org.example.interfaces;

import org.example.models.Character;
import org.example.models.Property;

import java.util.ArrayList;

public interface MunicipalityInterface {

//    Buy and sell property
    Property buyProperty();
    void sellProperty(Property property);
    void showProperties(Character character, ArrayList<Property> properties);
}
