package org.example.models;

import jdk.jshell.execution.LoaderDelegate;
import org.example.Database;
import org.example.defualtSystem.Bank;
import org.example.defualtSystem.Life;
import org.example.defualtSystem.Municipality;
import org.example.defualtSystem.StockMarket;
import org.example.interfaces.CityInterface;

import javax.management.Notification;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class City implements CityInterface {
    private final ArrayList<Character> characters;
    private final Bank bankSystem;
    public static Municipality municipality;

    private final StockMarket stockMarket;

    private Character root;

    public City() {
        characters = new ArrayList<>();
        municipality = new Municipality();
//        Get Bank Property from municipality
//        bank has bought for one time
        Property bankPlace = null;
        for (Property tempProperty : Database.LoadProperties()) {
            if (tempProperty.getOwner() == root)
                bankPlace = tempProperty;
        }
        bankSystem = new Bank(bankPlace, root);
//        bankSystem = new Bank(municipality.buyProperty(new float[]{40, 40}, new float[]{70, 170}, root), root);
        stockMarket = new StockMarket();
        stockMarket.startMarketSimulation();
    }

    public City(Boolean has, User user) {
        characters = Database.loadCharacter();
        Character temp = null;
        for (Character tempCharacter : characters) {
            if (tempCharacter.getUserInfo().getUsername().equals(user.getUsername())) {
                temp = tempCharacter;
            }
        }
        municipality = new Municipality();
        Property bankPlace = null;
        for (Property tempProperty : Database.LoadProperties()) {
            if (tempProperty.getOwner() == root)
                bankPlace = tempProperty;
        }
        bankSystem = new Bank(bankPlace, root);
        stockMarket = new StockMarket();
        stockMarket.startMarketSimulation();

        beginGame(temp);
    }

    public City(Boolean has) {
        characters = Database.loadCharacter();
        municipality = new Municipality();
        Property bankPlace = null;
        for (Property tempProperty : Database.LoadProperties()) {
            if (tempProperty.getOwner() == root)
                bankPlace = tempProperty;
        }
        bankSystem = new Bank(bankPlace, root);
        stockMarket = new StockMarket();
        stockMarket.startMarketSimulation();
    }

    @Override
    public void joinCharacter(User userinfo) {
        BankAccount newAccount = bankSystem.newAccount(userinfo.getUsername(), userinfo.getPassword());
        Database.createBankAccount(newAccount);
        Character character = new Character(userinfo, newAccount, new Life(), null, null, null);
        characters.add(character);
        Database.saveCharacter(character);
        beginGame(character);
    }

    @Override
    public void getCityDetail() {
        String players = Arrays.toString(characters.toArray());
    }


    /**
     * Begin Game function generate a new thread for each character ,<b > DO NOT CHANGE THIS FUNCTION STRUCTURE</b> ,
     */
    public void beginGame(Character character) {
        Thread thread = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("       *** welcome to panel ***");
                System.out.println("**************************************");
                System.out.println("*     go to place          [1]       *");
                System.out.println("*     process location     [2]       *");
                System.out.println("*     dashboard            [3]       *");
                System.out.println("*     life detail          [4]       *");
                System.out.println("*     exit                 [5]       *");
                System.out.println("**************************************");
                System.out.println("enter your command : ");
                while (true) {
                    switch (scanner.next()) {
                        case "1" -> GoTo(character);
                        case "2" -> Process_location(character);
                        case "3" -> Dashboard(character);
                        case "4" -> Life(character);
                        case "5" -> {
                            System.out.println("are you sure?[yes/no]");
                            if (scanner.next().equals("yes")) {
                                Exit();
                            } else {
                                beginGame(character);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    public void GoTo(Character character) {
        System.out.println("enter location or id or industry title from below");
        System.out.println("**********************");
        for (Property property : Database.LoadProperties()) {
            System.out.println("property id    : " + property.getId());
            System.out.println("title          : " + property.getIndustryTitle());
            System.out.println("coordinate     : [" + property.getCoordinate()[0] + "," + property.getCoordinate()[1] + "]");
            System.out.print("for sale       : " + (property.isForSale() ? "yes" : "no"));
            if (property.getOwner() == character) System.out.println("(you own)");
            else System.out.println("");

            System.out.println("********");
        }
        System.out.println("Tip: if you want travel by coordinate write in this order :(divide it by comma) X,Y");

        Scanner MyPlace = new Scanner(System.in);
        String place = MyPlace.nextLine();
        String placeX = "", placeY = "";
        float locationX = 0.0f, locationY = 0.0f;

        if (place.contains(",")) {
            String[] placeXY = place.split(",");
            placeX = placeXY[0];
            placeY = placeXY[1];

            locationX = Float.parseFloat(placeX);
            locationY = Float.parseFloat(placeY);
        }

        Property location = null;
        boolean isWrongData = false;
        for (Property property : Database.LoadProperties()) {
            if (property.getIndustryTitle().equals(place)) {
                location = property;
                isWrongData = true;
            } else if (String.valueOf(property.getId()).equals(place)) {
                isWrongData = true;
                location = property;
            } else if (locationX == property.getCoordinate()[0] && locationY == property.getCoordinate()[1]) {
                isWrongData = true;
                location = property;
            }

        }
        if (!isWrongData) {
            System.out.println("You Enter details wrongly !");
            System.out.println("please enter again...");
            GoTo(character);
        }
        character.gotToLocation(location);
        character.positionProcessing();

    }

    public void Process_location(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("showing where is character               [1]");
        System.out.println("showing option according to industry     [2]");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Character_Location(character);
            case "2" -> Ownership_Detail(character);
        }
    }

    public void Character_Location(Character character) {
        System.out.println("do somethimg");
    }

    public void Ownership_Detail(Character character) {
        System.out.println("do something");
    }

    public void Dashboard(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("myjob             [1]");
        System.out.println("properties        [2]");
        System.out.println("economy           [3]");
        System.out.println("notifications     [4]");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> My_Job(character);
            case "2" -> Properties(character);
            case "3" -> Economy(character);
            case "4" -> NotificationCenter(character);

        }
    }

    private void NotificationCenter(Character character) {
        System.out.println("do sth");
    }

    public void My_Job(Character character) {
        System.out.println("do something");
    }

    public void Properties(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("show properties         [1]");
        System.out.println("sell                    [2]");
        System.out.println("management              [3]");
        System.out.println("found industry          [4]");
        switch (scanner.next()) {
            case "1" -> Show_Properties(character);
            case "2" -> sell(character);
            case "3" -> Management(character);
            case "4" -> Found_Industry(character);
        }
    }

    public void Show_Properties(Character character) {
        System.out.println("do something");
    }

    public void sell(Character character) {
        System.out.println("do something");
    }

    public void Management(Character character) {
        System.out.println("do something");
    }

    public void Found_Industry(Character character) {
        System.out.println("do something");
    }

    public void Economy(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("show incomes          [1]");
        System.out.println("show job detail       [2]");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Show_Incomes(character);
            case "2" -> Show_Job_Detail(character);
        }
    }

    public void Show_Incomes(Character character) {
        System.out.println("do something");
    }

    public void Show_Job_Detail(Character character) {
        System.out.println("do something");
    }

    public void Life(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("life detail         [1]");
        System.out.println("sleep option        [2]");
        System.out.println("eat option          [3]");
        switch (scanner.next()) {
            case "1" -> Life_Detail(character);
            case "2" -> Sleep(character);
            case "3" -> Eat(character);
        }
    }

    public void Life_Detail(Character character) {
        System.out.println("do something");
    }

    public void Sleep(Character character) {
        System.out.println("do something");
    }

    public void Eat(Character character) {
        System.out.println("do something");
    }

    public void Exit() {
        System.exit(0);
    }
}
