package org.example.defualtSystem;

import org.example.Database;
import org.example.interfaces.MunicipalityInterface;
import org.example.models.Character;
import org.example.models.Property;

import java.util.ArrayList;
import java.util.Random;

public class Municipality implements MunicipalityInterface {
    public static ArrayList<Property> properties = new ArrayList<>();
    Random random = new Random();
    public final int WIDTH_MAP = 140;
    public final int HEIGHT_MAP = 240;



    public Municipality() {
        generateProperties();
    }

    private void generateProperties() {
//        width of map : 140
//        height of map : 190

//        we need it for just one time for creating city's buildings
//        generateForOnce();
    }

    private void generateForOnce() {


        final float WIDTH_PLACE = 40;
        final float HEIGHT_PLACE = 40;

        for (float y = 20; y <= HEIGHT_MAP - 20; y++) {
            for (float x = 20; x <= WIDTH_MAP - 20; x++) {
                Property tempProperty = new Property(new float[]{WIDTH_PLACE, HEIGHT_PLACE}
                        , new float[]{x, y}
                        , null);
                Database.saveProperty(tempProperty);
                x += 49;
            }
            y += 49;
        }

    }


    @Override
    public Property buyProperty() {
        return null;
    }

    @Override
    public void sellProperty(Property property) {

    }

    @Override
    public void showProperties() {

    }
}
