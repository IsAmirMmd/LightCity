package org.example.models;

import org.example.defualtSystem.Bank;
import org.example.defualtSystem.Life;
import org.example.defualtSystem.Municipality;
import org.example.defualtSystem.StockMarket;
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
        municipality = new Municipality();
//        Get Bank Property from municipality
        bankSystem = new Bank(new Property(new float[]{12, 32}, new float[]{42, 32}, root), root);
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

    @Override
    public void getCityDetail() {
        String players = Arrays.toString(characters.toArray());
    }


    /**
     * Begin Game function generate a new thread for each character ,<b > DO NOT CHANGE THIS FUNCTION STRUCTURE</b> ,
     */
    private void beginGame(Character character) {
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
                        case "1" -> GoTo();
                        case "2" -> Process_location();
                        case "3" -> Dashboard();
                        case "4" -> Life();
                        case "5" -> {
                            System.out.println("are you sure?");
                            System.out.println("enter yes/y ");
                            if (scanner.next() == "yes" || scanner.next() == "y") {
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

    public void GoTo() {
        System.out.println("enter location or id or industry title");
        Scanner MyPlace = new Scanner(System.in);
        String place = MyPlace.next();
    }

    public void Process_location() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("showing where is character               [1]");
        System.out.println("showing option according to industry     [2]");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Character_Location();
            case "2" -> Ownership_Detail();
        }
    }

    public void Character_Location() {
        System.out.println("do somethimg");
    }

    public void Ownership_Detail() {
        System.out.println("do something");
    }

    public void Dashboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("myjob             [1]");
        System.out.println("properties        [2]");
        System.out.println("economy           [3]");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> My_Job();
            case "2" -> Properties();
            case "3" -> Economy();
        }
    }

    public void My_Job() {
        System.out.println("do something");
    }

    public void Properties() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("show properties         [1]");
        System.out.println("sell                    [2]");
        System.out.println("management              [3]");
        System.out.println("found industry          [4]");
        switch (scanner.next()) {
            case "1" -> Show_Properties();
            case "2" -> sell();
            case "3" -> Management();
            case "4" -> Found_Industry();
        }
    }

    public void Show_Properties() {
        System.out.println("do something");
    }

    public void sell() {
        System.out.println("do something");
    }

    public void Management() {
        System.out.println("do something");
    }

    public void Found_Industry() {
        System.out.println("do something");
    }

    public void Economy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("show incomes          [1]");
        System.out.println("show job detail       [2]");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Show_Incomes();
            case "2" -> Show_Job_Detail();
        }
    }

    public void Show_Incomes() {
        System.out.println("do something");
    }

    public void Show_Job_Detail() {
        System.out.println("do something");
    }

    public void Life() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("life detail         [1]");
        System.out.println("sleep option        [2]");
        System.out.println("eat option          [3]");
        switch (scanner.next()) {
            case "1" -> Life_Detail();
            case "2" -> Sleep();
            case "3" -> Eat();
        }
    }

    public void Life_Detail() {
        System.out.println("do something");
    }

    public void Sleep() {
        System.out.println("do something");
    }

    public void Eat() {
        System.out.println("do something");
    }

    public void Exit() {
        System.exit(0);
    }
}
