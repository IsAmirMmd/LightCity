package org.example;

import org.example.defualtSystem.Bank;
import org.example.defualtSystem.Life;
import org.example.defualtSystem.Municipality;
import org.example.models.*;
import org.example.models.Character;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class Database {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3307/lightcity";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "";


    private Connection conn;

    public Database() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connecting to database...");
        } catch (Exception exp) {
            System.out.println("Database Exception : \n" + exp.toString());
            System.exit(0);
        }
    }
    //    Tables

    /**
     * Users
     */

    private void createTables() {
//        query example
        String query = "CREATE TABLE IF NOT EXISTS Users (username varchar(255) primary key ,password varchar(255))";
        try {
            Statement stmt = conn.createStatement();
            if (stmt.execute(query)) {
//               do nothing
            } else System.out.println("An error accord during operation");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static User loginGame(User user) {
        try {
            if (!isValid(user)) {
                return user;
            } else {
                System.out.println("we don't have user with these details!");
                System.out.println("you can check your information and enter it again.");
                return null;
            }
        } catch (Exception exception) {
        }
        return null;
    }

    public static void saveProperty(Property property) {
        String insertQuery = "INSERT INTO properties (width, height, x_coordinate, y_coordinate, owner) VALUES (  ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            float width = property.getScales()[0];
            float height = property.getScales()[1];
            float xCoordinate = property.getCoordinate()[0];
            float yCoordinate = property.getCoordinate()[1];

            statement.setFloat(1, width);
            statement.setFloat(2, height);
            statement.setFloat(3, xCoordinate);
            statement.setFloat(4, yCoordinate);
            if (property.getOwner() == null) {
                statement.setString(5, "mayor");
            } else {
                statement.setString(5, property.getOwner().getUserInfo().getUsername());
            }
            statement.executeUpdate();

            System.out.println("Properties saved to the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Property> LoadProperties() {
        Character root = null;
        Character mayor = null;
        ArrayList<Property> properties = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            // Execute a SELECT query
            String sql = "SELECT * FROM properties";
            ResultSet rs = statement.executeQuery(sql);

            // Process the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                float width = rs.getFloat("width");
                float height = rs.getFloat("height");
                float xCoordinate = rs.getFloat("x_coordinate");
                float yCoordinate = rs.getFloat("y_coordinate");
                String owner = rs.getString("owner");
                boolean iSForSale = rs.getBoolean("forsale");
                Property tempProperty = null;
                if (owner.equals("root")) {
                    tempProperty = new Property(new float[]{width, height}, new float[]{xCoordinate, yCoordinate}, root);
                } else if (owner.equals("mayor")) {
                    tempProperty = new Property(new float[]{width, height}, new float[]{xCoordinate, yCoordinate}, mayor);
                } else {
                    Character ownerPro = null;
                    for (Character temp : loadCharacter()) {
                        if (ownerPro.getUserInfo().getUsername().equals(owner)) {
                            ownerPro = temp;
                        }
                    }
                    tempProperty = new Property(new float[]{width, height}, new float[]{xCoordinate, yCoordinate}, ownerPro);
                }
                tempProperty.setId(id);
                tempProperty.setForSale(iSForSale);
                properties.add(tempProperty);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static boolean isValid(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static User registerGame(User user) {
        boolean isValidForReg = isValid(user);
        if (isValidForReg) {
            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement statement = connection.prepareStatement(insertQuery)) {

                String UserName = user.getUsername();
                String passWord = user.getPassword();

                statement.setString(1, UserName);
                statement.setString(2, passWord);
                statement.executeUpdate();

                System.out.println("user has been saved to the database.");
                return user;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("we have this user in our database");
            System.out.println("please back to the pre page and login with userName");
            return null;
        }
        return null;
    }

    public static void BuyProperty(Character owner, Property tempProperty) {
        String sql = "UPDATE properties SET owner=?, ForSale=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (owner == null) {
                statement.setString(1, "root");
            } else {
                statement.setString(1, owner.getUserInfo().getUsername());
            }
            statement.setBoolean(2, false);
            System.out.println(tempProperty.getId());
            statement.setInt(3, tempProperty.getId());

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveCharacter(Character character) {
        String insertQuery = "INSERT INTO `characters`(`username`, `password`, `money`, `life`, `job`, `location`) VALUES (?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            String username = character.getUserInfo().getUsername();
            String password = character.getUserInfo().getPassword();
            String life = character.getLife().getFood() + "," + character.getLife().getSleep() + "," + character.getLife().getWater();
            String jobTitle = "";
            if (character.getJob() == null) {
                jobTitle = "null";
            } else {
                jobTitle = character.getJob().getTitle();
            }
            String inLocation = " ";

            if (character.getInPosition() == null) {
                inLocation = "null";
            } else {
                inLocation = character.getInPosition().getCoordinate()[0] + "," + character.getInPosition().getCoordinate()[1];
            }

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setFloat(3, 0);
            statement.setString(4, life);
            statement.setString(5, jobTitle);
            statement.setString(6, inLocation);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Character> loadCharacter() {
        ArrayList<Character> characters = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            // Execute a SELECT query
            String sql = "SELECT * FROM characters";
            ResultSet rs = statement.executeQuery(sql);

            // Process the result set
            while (rs.next()) {

                String username = rs.getString("username");
                String pass = rs.getString("password");
                int money = rs.getInt("money");
                String life = rs.getString("life");
                String job = rs.getString("job");
                String location = rs.getString("location");

//                property arraylist ...
                ArrayList<Property> ownPro = new ArrayList<>();
                for (Property temp : LoadProperties()) {
                    if (temp.getOwner() != null) {
                        if (temp.getOwner().getUserInfo().getUsername().equals(username)) {
                            ownPro.add(temp);
                        }
                    }
                }
//                life details
                String[] Sep_life = life.split(",");
                float food = Float.parseFloat(Sep_life[0]);
                float sleep = Float.parseFloat(Sep_life[1]);
                float water = Float.parseFloat(Sep_life[2]);

                Life tempLife = new Life(food, water, sleep);
//
//                find char job
                Job UserJob = null;
                for (Job tempJob : loadJobs()) {
                    if (tempJob.getTitle().equals(job)) {
                        UserJob = tempJob;
                    }
                }

//                find user bank account
                BankAccount tempBankAccount = null;
                for (BankAccount bankAccount : loadBankAccounts()) {
                    if (bankAccount.getOwner().equals(username)) {
                        tempBankAccount = bankAccount;
                    }
                }

//                find in time coordinate
                Property tempProperty = null;
                if (!location.equals("null")){
                    String[] Sep_cord = location.split(",");
                    float x = Float.parseFloat(Sep_cord[0]);
                    float y = Float.parseFloat(Sep_cord[1]);
                    for (Property property : LoadProperties()) {
                        if (property.getCoordinate()[1] == y && property.getCoordinate()[0] == x) {
                            tempProperty = property;
                        }
                    }
                }


//                create temp user
                User tempUser = new User(username, pass);

                Character character = new Character(tempUser, tempBankAccount, tempLife, UserJob, ownPro, tempProperty);
                characters.add(character);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return characters;
    }

    public static void createBankAccount(BankAccount bankAccount) {
        String insertQuery = "INSERT INTO `bank-account`(`username`, `password`, `money`, `last`) VALUES (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            String username = bankAccount.getOwner();
            String password = bankAccount.getPassword();
            float money = bankAccount.getMoney();
            String last = bankAccount.getLastChange().toString();
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setFloat(3, money);
            statement.setString(4, last);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<BankAccount> loadBankAccounts() {
        String selectQuery = "SELECT * FROM `bank-account`";
        ArrayList<BankAccount> bankAccounts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                float money = resultSet.getFloat("money");
                String last = resultSet.getString("last");

                String dateString = last;
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
                Date date = new Date();
                try {
                    LocalDateTime localDateTime = LocalDateTime.parse(dateString, dateFormat);
                    date = Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }

                BankAccount bankAccount = new BankAccount(username, password, money, date);
                bankAccounts.add(bankAccount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bankAccounts;
    }

    public static void saveJob(Job job) {
        String insertQuery = "INSERT INTO `job`(`title`, `income`, `inid`) VALUES (?,?,?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            String title = job.getTitle();
            float money = job.getIncome();
            String IndustyID = job.getIndustryId();
            statement.setString(1, title);
            statement.setFloat(2, money);
            statement.setString(3, IndustyID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Job> loadJobs() {
        String selectQuery = "SELECT * FROM `job`";
        ArrayList<Job> jobs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                float income = resultSet.getFloat("income");
                String industryId = resultSet.getString("inid");
                Job job = new Job(title, income, industryId);
                jobs.add(job);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jobs;
    }

    public static void createCity() {
        String insertQuery = "INSERT INTO `city`(`isCity`) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setBoolean(1, true);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasCity() {
        String selectQuery = "SELECT COUNT(*) FROM `city` WHERE `isCity` = true";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}

