package org.example;

import org.example.interfaces.GameInterface;
import org.example.models.Character;
import org.example.models.City;
import org.example.models.User;

import java.util.ArrayList;

public class Game implements GameInterface {

    //    Check Data from Database or file to see There is city or not
    private City city;

    @Override
    public void continueGame(User user) {
        city = new City(true,user);
    }

    /**
     * Create new city and Generate new Character
     *
     * @param user : User information contain username, password
     */
    @Override
    public void startGame(User user) {
        if (Database.hasCity()) {
            city = new City(true);
        } else {
            Database.createCity();
            generateNewCity();
        }
        city.joinCharacter(user);
    }

    /**
     * @param ip   Server ip address / example : 127.0.0.1
     * @param port Server open port for specific ip address
     */
    @Override
    public void joinServer(String ip, int port) {

    }

    @Override
    public void generateNewCity() {
        city = new City();
    }

}
