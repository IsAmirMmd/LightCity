package org.example.defualtSystem;

import org.example.Database;
import org.example.interfaces.MunicipalityInterface;
import org.example.models.BankAccount;
import org.example.models.Character;
import org.example.models.Property;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Municipality implements MunicipalityInterface {
    public static ArrayList<Property> properties = Database.LoadProperties();
    private BankAccount account;
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
                Property tempProperty = new Property(new float[]{WIDTH_PLACE, HEIGHT_PLACE}, new float[]{x, y}, null);
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

    public Property buyProperty(float[] scales, float[] coordinate, Character owner) {
        account = owner.getAccount();
        Property returnProperty = null;
        for (Property tempProperty : properties) {
            if (tempProperty.getScales()[0] == scales[0] && tempProperty.getScales()[1] == scales[1] && tempProperty.getCoordinate()[0] == coordinate[0] && tempProperty.getCoordinate()[1] == coordinate[1]) {
                System.out.println("W : " + tempProperty.getScales()[0] + ", H : " + tempProperty.getScales()[1]);
                System.out.println("X : " + tempProperty.getCoordinate()[0] + ", Y : " + tempProperty.getCoordinate()[1]);
                returnProperty = tempProperty;
                owner.setProperties(returnProperty);
                account.withdraw(owner, tempProperty.getPrice());
                Database.updateCharacter("money", owner);
                Database.BuyProperty(owner, tempProperty);

            }
        }
        return returnProperty;
    }

    @Override
    public void sellProperty(Property property) {

    }

    @Override
    public void showProperties() {

    }
}
