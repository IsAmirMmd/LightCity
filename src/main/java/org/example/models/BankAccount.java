package org.example.models;

import org.example.Database;
import org.example.defualtSystem.Bank;

import java.util.Date;

public class BankAccount {
    private String owner;
    private String password;
    private float money;
    private Date lastChange;

    private String logs = "";

    public BankAccount(String owner, String password, float money, Date lastChange) {
        this.owner = owner;
        this.password = password;
        this.money = 10;
        this.lastChange = lastChange;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getMoney() {
        return money;
    }


    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public boolean withdraw(Character character, float amount) {
        if (character.getUserInfo().getUsername().equals(owner)) {
            if (amount <= money) {
                Bank.turnover.transfer(amount, -1);
                money -= amount;
                Database.updateBankAccount(character.getUserInfo().getUsername(), money);
                return true;
            } else
                return false;
        }
        return false;
    }

    public boolean deposit(Character character, float amount) {
        if (amount > 0) {
            String log = String.format("User : %s deposit %f \n", character.getUserInfo().getUsername(), amount);
            logs += log;
            Bank.turnover.transfer(amount, 1);
            money += amount;
            Database.updateBankAccount(character.getUserInfo().getUsername(), money);
            return true;
        }
        return false;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
