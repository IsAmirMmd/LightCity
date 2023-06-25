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
//        bank has bought for one time
        bankSystem = new Bank(municipality.buyProperty(new float[]{40, 40}, new float[]{70, 170}, root), root);
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
     *
     * */
    private void beginGame(Character character) {
        Thread thread = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("Show Menu");
                    switch (scanner.next()) {
                        case "2"-> System.out.println("Do Something");
                        case "3"-> System.out.println("Do Something");
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }
}
