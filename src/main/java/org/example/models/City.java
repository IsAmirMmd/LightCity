package org.example.models;

import org.example.Database;
import org.example.Game;
import org.example.defualtSystem.*;
import org.example.interfaces.CityInterface;

import javax.naming.spi.DirObjectFactory;
import javax.sql.rowset.serial.SerialStruct;
import java.util.*;

public class City implements CityInterface {
    public static ArrayList<Character> characters;
    public static ArrayList<Property> properties = Database.LoadProperties();
    private final Bank bankSystem;
    public static Municipality municipality;
    private final StockMarket stockMarket;

    private Character root = Database.root;

    //    for first time sign up and also without any tables.
    public City() {
        characters = new ArrayList<>();
        municipality = new Municipality();
//        create a bank
        Property bankPlace = municipality.buyPropertyForOne(new float[]{40, 40}, new float[]{70, 170}, root);
        bankSystem = new Bank(bankPlace, root);
        Database.createIndustry(bankSystem);
        Database.updatePropertyName(bankPlace, "Bank");
//        create a fast food shop
        Property fastShop = municipality.buyPropertyForOne(new float[]{40, 40}, new float[]{20, 170}, root);
        FastFoodShop fastFoodShop = new FastFoodShop("sandwich", fastShop, root);
        Database.createIndustry(fastFoodShop);
        Database.updatePropertyName(fastShop, fastFoodShop.getTitle());
//        run stock market
        stockMarket = new StockMarket();
        stockMarket.startMarketSimulation();
    }

    //for login and another logs
    public City(Boolean has, User user) {
        characters = Database.loadCharacter();
        Character temp = null;
        for (Character tempCharacter : characters) {
            if (tempCharacter.getUserInfo().getUsername().equals(user.getUsername())) {
                temp = tempCharacter;
            }
        }
        municipality = new Municipality();
//        fast food shop
        Property fastShop = null;
        for (Property tempProperty : properties) {
            if (tempProperty.getOwner() == root)
                if (tempProperty.getCoordinate()[0] == 20)
                    if (tempProperty.getCoordinate()[1] == 170) {
                        fastShop = tempProperty;
                        break;
                    }
        }
        FastFoodShop fastFoodShop = new FastFoodShop("sandwich", fastShop, root);
        Database.createIndustry(fastFoodShop);
        Database.updatePropertyName(fastShop, fastFoodShop.getTitle());
//        bank place
        Property bankPlace = null;
        for (Property tempProperty : properties) {
            if (tempProperty.getOwner() == root)
                if (tempProperty.getCoordinate()[0] == 70)
                    if (tempProperty.getCoordinate()[1] == 170) {
                        bankPlace = tempProperty;
                        break;
                    }
        }
        bankSystem = new Bank(bankPlace, root);
        stockMarket = new StockMarket();
        stockMarket.startMarketSimulation();
        beginGame(temp);
    }

    //    for first sign up but also there's a city and tables
    public City(Boolean has) {
        characters = Database.loadCharacter();
        municipality = new Municipality();
        //        fast food shop
        Property fastShop = null;
        for (Property tempProperty : properties) {
            if (tempProperty.getOwner() == root)
                if (tempProperty.getCoordinate()[0] == 20)
                    if (tempProperty.getCoordinate()[1] == 170) {
                        fastShop = tempProperty;
                        break;
                    }
        }
        FastFoodShop fastFoodShop = new FastFoodShop("sandwich", fastShop, root);
        Database.createIndustry(fastFoodShop);
        Database.updatePropertyName(fastShop, fastFoodShop.getTitle());
        Property bankPlace = null;
        for (Property tempProperty : properties) {
            if (tempProperty.getOwner() == root)
                if (tempProperty.getCoordinate()[0] == 70)
                    if (tempProperty.getCoordinate()[1] == 170) {
                        bankPlace = tempProperty;
                        break;
                    }
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
//        using thread to save details for prevent from unsaved change due to system shutdown
        Thread savingLife = new Thread(() -> {
            while (true) {
                Database.updateCharacter("life", character);
                try {
                    Thread.sleep(60000 * 2); // save each 2 minute
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        savingLife.start();

        Thread thread = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("**************************************");
                System.out.println("****       welcome to panel       ****");
                System.out.println("**************************************");
                System.out.println("*     go to place          [1]       *");
                System.out.println("*====================================*");
                System.out.println("*     process location     [2]       *");
                System.out.println("*====================================*");
                System.out.println("*     dashboard            [3]       *");
                System.out.println("*====================================*");
                System.out.println("*     life                 [4]       *");
                System.out.println("*====================================*");
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
        System.out.println("***************************************");
        System.out.println("****         go to place           ****");
        System.out.println("***************************************");
        municipality.showProperties(character, Database.LoadProperties());
        System.out.println("Tip: if you want travel by coordinate write in this order :(divide it by comma) X,Y");

        Scanner MyPlace = new Scanner(System.in);
        String place = MyPlace.nextLine();
        String placeX = "", placeY = "";
        float locationX = 0.0f, locationY = 0.0f;
        System.out.println(place);
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
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("****************************************************");
        System.out.println("****             process location               ****");
        System.out.println("****************************************************");
        System.out.println("*   showing where is character               [1]   *");
        System.out.println("*==================================================*");
        System.out.println("*   showing option according to industry     [2]   *");
        System.out.println("*==================================================*");
        System.out.println("*   back                                     [3]   *");
        System.out.println("****************************************************");
        System.out.println("enter your command:");
        switch (scanner1.next()) {
            case "1" -> Character_Location(character);
            case "2" -> Ownership_Detail(character);
            case "3" -> beginGame(character);
        }
    }

    public void Character_Location(Character character) {
        Property inLocation = character.getInPosition();
        if (inLocation == null) {
            System.out.println("you are in the base");
            System.out.println("you can travel and then check your location");
        } else if (inLocation.getIndustryTitle().equals("not-industry")) {
            System.out.println("you are here : [" + inLocation.getCoordinate()[0] + "," + inLocation.getCoordinate()[1] + "]");
        } else if (!inLocation.getIndustryTitle().equals("not-industry")) {
            System.out.println("you are here : [" + inLocation.getCoordinate()[0] + "," + inLocation.getCoordinate()[1] + "]");
            System.out.println("at the " + inLocation.getIndustryTitle());
        }
        Scanner loca = new Scanner(System.in);
        System.out.println("******************************************************");
        System.out.println("*     what do you want to do now?                    *");
        System.out.println("*????????????????????????????????????????????????????*");
        System.out.println("*     showing option according to industry     [1]   *");
        System.out.println("*====================================================*");
        System.out.println("*     back to main menu                        [2]   *");
        System.out.println("******************************************************");
        System.out.println("enter your command:");
        switch (loca.next()) {
            case "1" -> Ownership_Detail(character);
            case "2" -> beginGame(character);
        }
    }

    public void Ownership_Detail(Character character) {
        Property inLocation = character.getInPosition();
        if (inLocation == null) {
            System.out.println("you are in the base");
            System.out.println("you can travel and then check your location");
        } else if (inLocation.getIndustryTitle().equals("not-industry")) {
            System.out.println("you are here : [" + inLocation.getCoordinate()[0] + "," + inLocation.getCoordinate()[1] + "]");
            System.out.println("you can make your business here :)");
            if (inLocation.getOwner().equals(root)) {
                System.out.println("owner of this place is Mayor and his dear wife !");
            } else {
                System.out.println("owner of this place is Mr/Ms " + inLocation.getOwner().getUserInfo().getUsername());
            }
        } else if (!inLocation.getIndustryTitle().equals("not-industry")) {
            System.out.println("you are here : [" + inLocation.getCoordinate()[0] + "," + inLocation.getCoordinate()[1] + "]");
            System.out.println("at the " + inLocation.getIndustryTitle());
            if (inLocation.getOwner().equals(root)) {
                System.out.println("owner of this place is Mayor and his dear wife !");
            } else {
                System.out.println("owner of this place is Mr/Ms " + inLocation.getOwner().getUserInfo().getUsername());
            }
        }
        Scanner owDeta = new Scanner(System.in);
        System.out.println("*****************************************");
        System.out.println("*     what do you want to do now?       *");
        System.out.println("*???????????????????????????????????????*");
        System.out.println("*     back to main menu       [1]       *");
        System.out.println("*****************************************");
        System.out.println("enter your command:");
        switch (owDeta.next()) {
            case "1" -> beginGame(character);
        }
    }

    public void Dashboard(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*************************************");
        System.out.println("****          dashboard          ****");
        System.out.println("*************************************");
        System.out.println("*      my job status     [1]        *");
        System.out.println("*===================================*");
        System.out.println("*      properties        [2]        *");
        System.out.println("*===================================*");
        System.out.println("*      economy           [3]        *");
        System.out.println("*===================================*");
        System.out.println("*      notifications     [4]        *");
        System.out.println("*===================================*");
        System.out.println("*      back              [5]        *");
        System.out.println("*************************************");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> My_Job(character);
            case "2" -> Properties(character);
            case "3" -> Economy(character);
            case "4" -> NotificationCenter(character);
            case "5" -> beginGame(character);
        }
    }

    private void NotificationCenter(Character character) {
        Property requestPro = null;
        String[] xANDy = {"", ""};
        for (Request any : Database.loadRequests()) {
            if (any.getOldOwner().equals(character.getUserInfo().getUsername())) {
                System.out.println("request ID    : " + any.getId());
                System.out.println("from          : " + any.getNewOwner());
                for (Property property : character.getProperties()) {
                    xANDy[0] = any.getCoordinates().split(",")[0];
                    xANDy[1] = any.getCoordinates().split(",")[1];
                    if (Float.parseFloat(xANDy[0]) == property.getCoordinate()[0] && Float.parseFloat(xANDy[1]) == property.getCoordinate()[1]) {
                        requestPro = property;
                    }
                }
                System.out.println("for property in this location : [" + any.getCoordinates() + "]");
                System.out.println("with price of : " + requestPro.getPrice() + "$");
                System.out.println("*********************");
            }
        }

        System.out.println("*   back to dashboard     [1]  *");
        Scanner scanner = new Scanner(System.in);
        System.out.println("*************************************");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Dashboard(character);
        }
    }


    public void My_Job(Character character) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("  ***   your job info will be here ***");
        Job temp = null;
        for (Job job : Database.loadJobs()) {
            if (job.getUser().equals(character.getUserInfo().getUsername())) {
                temp = job;
            }
        }
        if (temp == null) {
            System.out.println("you are non work!");
        } else {
            System.out.println("your work is : " + temp.getTitle());
            System.out.println("*      check job info    [1]        *");
            System.out.println("*===================================*");
        }
        System.out.println("*      back              [2]        *");
        System.out.println("*===================================*");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Economy(character);
            case "2" -> Dashboard(character);
        }
    }

    public void Properties(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("****************************************");
        System.out.println("***    welcome to your properties    ***");
        System.out.println("****************************************");
        System.out.println("*    show properties         [1]       *");
        System.out.println("*======================================*");
        System.out.println("*    management              [2]       *");
        System.out.println("*======================================*");
        System.out.println("*    found industry          [3]       *");
        System.out.println("*======================================*");
        System.out.println("*    back                    [4]       *");
        System.out.println("****************************************");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Show_Properties(character);
            case "2" -> Management(character);
            case "3" -> Found_Industry(character);
            case "4" -> Dashboard(character);
        }
    }

    public void Show_Properties(Character character) {
        System.out.println("you can check all of your property from here!");
        System.out.println("*****************");
        municipality.showProperties(character, character.getProperties());
        Scanner scanner = new Scanner(System.in);
        System.out.println("here is all of your property ...");
        System.out.println("****************************************");
        System.out.println("*    management              [1]       *");
        System.out.println("*======================================*");
        System.out.println("*    found industry          [2]       *");
        System.out.println("*======================================*");
        System.out.println("*    back                    [3]       *");
        System.out.println("****************************************");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Management(character);
            case "2" -> Found_Industry(character);
            case "3" -> Properties(character);
        }
    }

    public void sell(Character character, Request request) {
        Property requestForSell = null;
        String newOwnerUserName = "";
        String[] xANDy = {"", ""};
        for (Request any : Database.loadRequests()) {
            if (any.getOldOwner().equals(character.getUserInfo().getUsername())) {
                for (Property property : character.getProperties()) {
                    xANDy[0] = any.getCoordinates().split(",")[0];
                    xANDy[1] = any.getCoordinates().split(",")[1];
                    if (Float.parseFloat(xANDy[0]) == property.getCoordinate()[0] && Float.parseFloat(xANDy[1]) == property.getCoordinate()[1]) {
                        requestForSell = property;
                    }
                }
                newOwnerUserName = any.getNewOwner();
            }
        }
        Character ewOwner = null;
        for (Character character1 : Database.loadCharacter()) {
            if (character1.getUserInfo().getUsername().equals(newOwnerUserName)) {
                ewOwner = character1;
            }
        }
        System.out.println("are you sure?[y/n]");
        Scanner sell = new Scanner(System.in);
        if (sell.nextLine().equals("y")) {
            Database.removeRequest(request);
            municipality.tradeProperties(character, ewOwner, requestForSell);
        } else {
            Management(character);
        }
    }

    public void Management(Character character) {
        Scanner manage = new Scanner(System.in);
        System.out.println("*************************************************************");
        System.out.println("*** you can manage your requests and properties from here ***");
        System.out.println("*************************************************************");
        System.out.println("*          make property ready for sell    [1]              *");
        System.out.println("*===========================================================*");
        System.out.println("*          set price for your property     [2]              *");
        System.out.println("*===========================================================*");
        System.out.println("*          manage request for property     [3]              *");
        System.out.println("*===========================================================*");
        System.out.println("*          back to menu                    [4]              *");
        System.out.println("*************************************************************");
        System.out.println("enter your command : ");
        switch (manage.nextInt()) {
            case 1 -> {
                System.out.println("here is all of your property");
                municipality.showProperties(character, character.getProperties());
                System.out.println("which one you want to make ready for sell?");
                Scanner MyPlace = new Scanner(System.in);
                String place = MyPlace.nextLine();

                Property location = null;
                boolean isWrongData = false;
                for (Property property : Database.LoadProperties()) {
                    if (String.valueOf(property.getId()).equals(place)) {
                        isWrongData = true;
                        location = property;
                    }
                }
                if (!isWrongData) {
                    System.out.println("You Enter details wrongly !");
                    System.out.println("please enter again...");
                    Management(character);
                }
                Database.readyToSell(character, location, location.getPrice());
                System.out.println("you make this property ready to sell!");
                Management(character);
            }
            case 2 -> {
                System.out.println("here is all of your property");
                municipality.showProperties(character, character.getProperties());
                System.out.println("which one you want to change its price?");
                Scanner MyPlace = new Scanner(System.in);
                String place = MyPlace.nextLine();
                Property location = null;
                boolean isWrongData = false;
                for (Property property : properties) {
                    if (String.valueOf(property.getId()).equals(place)) {
                        isWrongData = true;
                        location = property;
                    }
                }
                if (!isWrongData) {
                    System.out.println("You Enter details wrongly !");
                    System.out.println("please enter again...");
                    Management(character);
                }
                System.out.println("enter price : (1.5 < price < 8)");
                float price = MyPlace.nextFloat();
                Database.readyToSell(character, location, price);
                System.out.println("you make this property ready to sell for " + price + "$ !");
                Management(character);
            }
            case 3 -> manageRequest(character);
            case 4 -> Properties(character);
        }
    }

    public void manageRequest(Character character) {
        Property requestForSell = null;
        String[] xANDy = {"", ""};
        for (Request any : Database.loadRequests()) {
            if (any.getOldOwner().equals(character.getUserInfo().getUsername())) {
                System.out.println("request ID    : " + any.getId());
                System.out.println("from          : " + any.getNewOwner());
                for (Property property : character.getProperties()) {
                    xANDy[0] = any.getCoordinates().split(",")[0];
                    xANDy[1] = any.getCoordinates().split(",")[1];
                    if (Float.parseFloat(xANDy[0]) == property.getCoordinate()[0] && Float.parseFloat(xANDy[1]) == property.getCoordinate()[1]) {
                        requestForSell = property;
                    }
                }
                System.out.println("for property in this location : [" + any.getCoordinates() + "]");
                System.out.println("with price of : " + requestForSell.getPrice() + "$");
                System.out.println("*********************");
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("if you want to accept any request please enter id");
        int reqID = scanner.nextInt();
        for (Request request : Database.loadRequests()) {
            if (request.getId() == reqID) {
                sell(character, request);
            }
        }
        System.out.println("*   back     [0]  *");
        System.out.println("*************************************");
        System.out.println("enter your command:");
        switch (scanner.nextInt()) {
            case 0 -> Management(character);
        }
    }

    public void Found_Industry(Character character) {
        Scanner found = new Scanner(System.in);
        System.out.println("for making industry you have to : ");
        System.out.println("   1 - buy property");
        System.out.println("   2 - create product");
        System.out.println("   3 - give salary to employee");
        System.out.println("*************");
        System.out.println("in this list you can check the property that you have :");
        municipality.showProperties(character, character.getProperties());
        if (character.getProperties().size() == 0) {
            System.out.println("you don't have any property");
            System.out.println("you can buy property with [1]");
            System.out.println("back                      [2]");
            switch (found.nextInt()) {
                case 1:
                    GoTo(character);
                case 2:
                    Properties(character);
            }
        }
        System.out.println("which one do you want to lunch business in it?");
        System.out.println("enter ID of property ...");
        int propID = found.nextInt();
        for (Property property : Database.LoadProperties()) {
            if (propID == property.getId()) {
                if (property.getIndustryTitle().equals("not-industry"))
                    makePropIndustry(character, property);
                else System.out.println("Error : You can't make another business in this place!");
            }
        }

        System.out.println("*   back     [0]  *");
        System.out.println("*************************************");
        System.out.println("enter your command:");
        switch (found.nextInt()) {
            case 0 -> Management(character);
        }
    }

    public void makePropIndustry(Character character, Property property) {
        Scanner income = new Scanner(System.in);
        System.out.println("enter your employees' income  :");
        float employeeIncome = income.nextFloat();
        System.out.println("enter title for your industry :");
        String title = income.next();
        System.out.println("you make your industry with name : " + title + " successfully");
        Job tempJob = new Job(title, 3, title + property.getId(), character.getUserInfo().getUsername());
        character.setJob(tempJob);
//        creating industry object
        Industry industry = new Industry(title, property, character, employeeIncome);
//        update data in database
        Database.createIndustry(industry);
        Database.saveJob(tempJob);
        Database.updateCharacter("job", character);
        Database.updatePropertyName(property, title);
        Database.cancelForSell(character, property);
    }


    public void Economy(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("***************************************");
        System.out.println("***     welcome to your economy     ***");
        System.out.println("***************************************");
        System.out.println("*      show incomes          [1]      *");
        System.out.println("*=====================================*");
        System.out.println("*      show job detail       [2]      *");
        System.out.println("*=====================================*");
        System.out.println("*      back                  [3]      *");
        System.out.println("***************************************");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Show_Incomes(character);
            case "2" -> Show_Job_Detail(character);
            case "3" -> Dashboard(character);
        }
    }

    public void Show_Incomes(Character character) {
        System.out.println("you don't have any income");
    }

    public void Show_Job_Detail(Character character) {
        System.out.println("do something");
    }

    public void Life(Character character) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*************************************");
        System.out.println("****             life            ****");
        System.out.println("*************************************");
        System.out.println("*      life detail         [1]      *");
        System.out.println("*===================================*");
        System.out.println("*      sleep option        [2]      *");
        System.out.println("*===================================*");
        System.out.println("*      eat option          [3]      *");
        System.out.println("*===================================*");
        System.out.println("*      back                [4]      *");
        System.out.println("*************************************");
        System.out.println("enter your command:");
        switch (scanner.next()) {
            case "1" -> Life_Detail(character);
            case "2" -> Sleep(character);
            case "3" -> Eat(character);
            case "4" -> beginGame(character);
        }
    }

    public void Life_Detail(Character character) {
        Scanner lifeDetails = new Scanner(System.in);
        System.out.println("*  you can manage and check your life status :  *");
        System.out.println("*  food  : " + character.getLife().getFood() + "  *");
        System.out.println("*  water : " + character.getLife().getWater() + "  *");
        System.out.println("*  sleep : " + character.getLife().getSleep() + "  *");
        System.out.println("*************");
        System.out.println("*  take a nap              [1] *");
        System.out.println("*  eat or drink            [2] *");
        System.out.println("*  back                    [3] *");
        System.out.println("enter your command:");
        switch (lifeDetails.next()) {
            case "1" -> Sleep(character);
            case "2" -> Eat(character);
            case "3" -> Life(character);
        }
    }

    public void Sleep(Character character) {
        Scanner sleep = new Scanner(System.in);
        System.out.println("* we have some plan for you *");
        System.out.println("* sleep : " + character.getLife().getSleep() + "  *");
        System.out.println("* +10 for 1$           [1]  *");
        System.out.println("* +20 for 2$           [2]  *");
        System.out.println("* +30 for 2.5$         [3]  *");
        System.out.println("* cancel and back      [4]  *");
        System.out.println("enter your command:");
        switch (sleep.next()) {
            case "1" -> sleepCharge(character, 10, 1);
            case "2" -> sleepCharge(character, 20, 2);
            case "3" -> sleepCharge(character, 30, 2.5f);
            case "4" -> Life_Detail(character);
        }
        System.out.println("************");
        System.out.println("what do you want?");
        System.out.println("*  charge again     [1]  *");
        System.out.println("*  back             [2]  *");
        switch (sleep.next()) {
            case "1" -> Sleep(character);
            case "2" -> Life_Detail(character);
        }
    }

    private void sleepCharge(Character character, float moreSleep, float price) {
        BankAccount account = character.getAccount();
        account.withdraw(character, price);

        float lastSleep = character.getLife().getSleep();
        float nowSleep = lastSleep + moreSleep;
        if (nowSleep >= 100) {
            nowSleep = 100.0f;
        }

        Life temp = character.getLife();
        temp.setSleep(nowSleep);

        Database.updateCharacter("life", character);

        System.out.println("your new sleep status is :" + temp.getSleep());
    }

    public void Eat(Character character) {
        Scanner foodScan = new Scanner(System.in);
        System.out.println("you can consume product to gain water or food");
        System.out.println("* travel and shop          [1] *");
        System.out.println("* cancel and back          [2] *");

        switch (foodScan.next()) {
            case "1" -> industriesBar(character);
            case "2" -> Life_Detail(character);
        }
    }

    public void industriesBar(Character character) {
        ArrayList<Property> industries = new ArrayList<>();
        for (Property property : Database.LoadProperties()) {
            if (!property.getIndustryTitle().equals("not-industry")) {
                industries.add(property);
            }
        }
        municipality.showProperties(character, industries);

        Scanner MyPlace = new Scanner(System.in);
        String place = MyPlace.nextLine();
        String placeX = "", placeY = "";
        float locationX = 0.0f, locationY = 0.0f;
        System.out.println(place);
        if (place.contains(",")) {
            String[] placeXY = place.split(",");
            placeX = placeXY[0];
            placeY = placeXY[1];

            locationX = Float.parseFloat(placeX);
            locationY = Float.parseFloat(placeY);
        }

        Property location = null;
        boolean isWrongData = false;
        for (Property property : industries) {
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
        shopProcessing(character, location);

    }

    public void shopProcessing(Character character, Property property) {
        BankAccount account = character.getAccount();
        Scanner PropertyScan = new Scanner(System.in);
        Property inTime = character.getInPosition();
        System.out.println("** you are here : " + inTime.getCoordinate()[0] + "," + inTime.getCoordinate()[1] + " **");
        System.out.println("what do you want to do?");
        System.out.println("1. buy industry's products");
        System.out.println("2. cancel and back");
        try {
            Scanner test = new Scanner(System.in);
            switch (PropertyScan.nextInt()) {
                case 1 -> {
                    if (property.getIndustryTitle().equals("sandwich")) {
                        System.out.println("1. hotdog ******** 0.23$ ***** 10F & 10W");
                        System.out.println("2. hamburger ***** 0.30$ ***** 18F & 15W");
                        System.out.println("3. pizza ********* 0.33$ ***** 22F & 20W");
                        System.out.println("4. soda ********** 0.13$ *********** 10W");
                        String testFood = test.next();
                        if (testFood.equals("1")) {
                            System.out.println("you bought hotdog successfully for 0.23$");
                            character.getLife().setFood(character.getLife().getFood() + 10.0f);
                            character.getLife().setWater(character.getLife().getWater() + 10.0f);
                            character.getAccount().withdraw(character, 0.23f);
                            Database.updateCharacter("life", character);
                            Database.updateBankAccount(character.getAccount().getOwner(), character.getAccount().getMoney(), character.getAccount().getLastChange());
                        } else if (testFood.equals("2")) {
                            System.out.println("you bought hamburger successfully for 0.30$");
                            character.getLife().setFood(character.getLife().getFood() + 18.0f);
                            character.getLife().setWater(character.getLife().getWater() + 15.0f);
                            character.getAccount().withdraw(character, 0.30f);
                            Database.updateCharacter("life", character);
                            Database.updateBankAccount(character.getAccount().getOwner(), character.getAccount().getMoney(), character.getAccount().getLastChange());
                        } else if (testFood.equals("3")) {
                            System.out.println("you bought pizza successfully for 0.33$");
                            character.getLife().setFood(character.getLife().getFood() + 22.0f);
                            character.getLife().setWater(character.getLife().getWater() + 20.0f);
                            character.getAccount().withdraw(character, 0.33f);
                            Database.updateCharacter("life", character);
                            Database.updateBankAccount(character.getAccount().getOwner(), character.getAccount().getMoney(), character.getAccount().getLastChange());
                        } else if (testFood.equals("4")) {
                            System.out.println("you bought soda successfully for 0.33$");
                            character.getLife().setWater(character.getLife().getWater() + 10.0f);
                            character.getAccount().withdraw(character, 0.13f);
                            Database.updateCharacter("life", character);
                            Database.updateBankAccount(character.getAccount().getOwner(), character.getAccount().getMoney(), character.getAccount().getLastChange());
                        }
                    }
                    System.out.println("have a nice meal!");
                    System.out.println("we're redirecting you to life details ...");
                    Life_Detail(character);
                }
                case 2 -> {
                    Eat(character);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            shopProcessing(character, property);
        }
    }


    public void Exit() {
        System.exit(0);
    }
}
