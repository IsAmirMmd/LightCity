package org.example.defualtSystem;

import org.example.Database;
import org.example.interfaces.MunicipalityInterface;
import org.example.models.BankAccount;
import org.example.models.Character;
import org.example.models.Property;
import org.example.models.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Municipality implements MunicipalityInterface {
    public static ArrayList<Character> characters = Database.loadCharacter();
    public static ArrayList<Property> properties = Database.LoadProperties();
    private BankAccount account;
    public final int WIDTH_MAP = 140;
    public final int HEIGHT_MAP = 240;

    public static Character root = new Character(new User("root", "1234"), new BankAccount("root", "1234", 100, null), null, null, null, null);

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
                Property tempProperty = new Property(new float[]{WIDTH_PLACE, HEIGHT_PLACE}, new float[]{x, y}, root);
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

    public Property buyPropertyForOne(float[] scales, float[] coordinate, Character owner) {
        account = owner.getAccount();
        Property returnProperty = null;
        for (Property tempProperty : Database.LoadProperties()) {
            if (tempProperty.getScales()[0] == scales[0] && tempProperty.getScales()[1] == scales[1] && tempProperty.getCoordinate()[0] == coordinate[0] && tempProperty.getCoordinate()[1] == coordinate[1]) {
                System.out.println("W : " + tempProperty.getScales()[0] + ", H : " + tempProperty.getScales()[1]);
                System.out.println("X : " + tempProperty.getCoordinate()[0] + ", Y : " + tempProperty.getCoordinate()[1]);
                returnProperty = tempProperty;
                owner.setProperties(returnProperty);
                Database.updateCharacter("money", owner);
                Database.BuyProperty(owner, tempProperty);
            }
        }
        return returnProperty;
    }

    public Property buyProperty(float[] scales, float[] coordinate, Character owner) {
        account = owner.getAccount();
        Property returnProperty = null;
        for (Property tempProperty : Database.LoadProperties()) {
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
        Character owner = property.getOwner();
        account = owner.getAccount();
        account.deposit(owner, property.getPrice());
    }

    public void tradeProperties(Character oldOwner, Character newOwner, Property property) {
        System.out.println("you are here for trading this property");
        System.out.println("W : " + property.getScales()[0] + ", H : " + property.getScales()[1]);
        System.out.println("X : " + property.getCoordinate()[0] + ", Y : " + property.getCoordinate()[1]);
        BankAccount OldOwner = oldOwner.getAccount();
        BankAccount NewOwner = newOwner.getAccount();

        NewOwner.withdraw(newOwner, property.getPrice());
        OldOwner.deposit(oldOwner, property.getPrice());

        oldOwner.getProperties().remove(property);
        newOwner.setProperties(property);

        Database.updateCharacter("money", newOwner);
        Database.updateCharacter("money", oldOwner);
        System.out.println("Nice Deal !");
        Database.BuyProperty(newOwner, property);
    }


    @Override
    public void showProperties(Character character, ArrayList<Property> properties) {
        for (Property property : properties) {
            System.out.println("property id    : " + property.getId());
            System.out.println("title          : " + property.getIndustryTitle());
            System.out.println("coordinate     : [" + property.getCoordinate()[0] + "," + property.getCoordinate()[1] + "]");
            System.out.println("owner          : " + property.getOwner().getUserInfo().getUsername());
            System.out.print("for sale       : " + (property.isForSale() ? "yes" : "no"));
            if (property.getOwner().equals(character)) System.out.println("(you own)");
            else System.out.println("");

            System.out.println("********");
        }
    }
}
