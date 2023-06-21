package org.example;

import org.example.defualtSystem.Municipality;
import org.example.models.Character;
import org.example.models.Property;
import org.example.models.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/lightcity";

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
            } else
                System.out.println("An error accord during operation");
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

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

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
        ArrayList<Property> properties = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

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

                Property tempProperty = new Property(new float[]{width, height}, new float[]{xCoordinate, yCoordinate}, null);
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
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

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

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 PreparedStatement statement = connection.prepareStatement(insertQuery)) {

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

}
