package org.example.models;

import org.example.Database;
import org.example.Game;
import org.example.defualtSystem.Life;
import org.example.defualtSystem.Municipality;
import org.example.interfaces.CharacterInterface;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Character implements CharacterInterface {
    private User userInfo;
    private BankAccount account;
    private Life life;

    private Job job;
    private ArrayList<Property> properties = new ArrayList<>();

    private Property inPosition;
    Character root;
    Character mayor;
    private City city;
    private Municipality municipality;

    public Character(User userInfo, BankAccount account, Life life, Job job, ArrayList<Property> properties, Property inPosition) {
        this.userInfo = userInfo;
        this.account = account;
        this.life = life;
        this.job = job;
        this.properties = properties;
        this.inPosition = inPosition;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void gotToLocation(Property destination) {
        if (destination == null) return;
        setInPosition(destination);
        Database.updateCharacter("location", this);
    }

    public ArrayList<Property> getProperties() {
        ArrayList<Property> propertyList = new ArrayList<>();

        for (Property property : City.properties) {
            if (property.getOwner() != null && property.getOwner() == this)
                propertyList.add(property);
        }
        return propertyList;
    }

    public void setProperties(Property property) {
        properties.add(property);
    }

    public Property getInPosition() {
        return inPosition;
    }

    public void setInPosition(Property inPosition) {
        this.inPosition = inPosition;
    }

    @Override
    public void positionProcessing() {
        account = this.getAccount();
        Scanner PropertyScan = new Scanner(System.in);
        Property inTime = this.getInPosition();
        System.out.println("** you are here : " + inTime.getCoordinate()[0] + "," + inTime.getCoordinate()[1] + " **");
        System.out.println("what do you want to do?");
        System.out.println("1. buy from mayor/players");
        System.out.println("2. to be hired");
        System.out.println("3. buy industry's products");
        try {
            switch (PropertyScan.nextInt()) {
                case 1 -> {
                    if (inTime.getOwner() == mayor && inTime.isForSale()) {
                        System.out.println("you can buy this place from mayor");
                        if (inTime.getPrice() <= this.getAccount().getMoney()) {
                            System.out.println("if you want to buy this,");
                            System.out.println("   1 - first look at your money :" + this.getAccount().getMoney() + "$");
                            System.out.println("   2 - and the worth of property :" + inTime.getPrice() + "$");
                            System.out.println("   3 - for confirm purchase enter[y/n]?");
                            if (PropertyScan.nextLine().equals("y")) {
                                City.municipality.buyProperty(new float[]{40, 40}, new float[]{inTime.getCoordinate()[0], inTime.getCoordinate()[1]}, this);
                            } else {
                                System.out.println("payment canceled!");
                                positionProcessing();
                            }
                        }
                    } else if (inTime.getOwner() != mayor && inTime.getOwner() != root && inTime.isForSale()) {
//                        it sends request to property's owner and he/she can check from notification bar
                        Database.addRequset(inTime.getOwner(), this, inTime);
                    }
                }
                case 2 -> {
                    if (!inTime.getIndustryTitle().equals("not-industry")) {
//                   add as employee
                    }
                }
                case 3 -> {
                    if (!inTime.getIndustryTitle().equals("not-")) {
//                   show its product
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            positionProcessing();
        }

        Game.city.beginGame(this);
    }
}
