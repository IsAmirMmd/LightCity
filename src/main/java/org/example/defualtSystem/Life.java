package org.example.defualtSystem;

import org.example.Database;
import org.example.models.City;
import org.example.models.Food;
import org.example.models.Liquid;

public class Life {
    private float food;
    private float water;
    private float sleep;

    public Life() {
        food = 100.0f;
        water = 100.0f;
        sleep = 100.0f;
    }

    public Life(float food, float water, float sleep) {
        this.food = food;
        this.water = water;
        this.sleep = sleep;
        startConsuming();
    }

    public float getFood() {
        return food;
    }

    public float getWater() {
        return water;
    }

    public float getSleep() {
        return sleep;
    }


    public void foodConsumption(Food product) {
        if (product.available) {
            water += product.getWater();
            food += product.getFood();
        }
    }

    public void liquidConsumption(Liquid product) {
        if (product.available) {
            water += product.getLiquid();
        }
    }


    public void startConsuming() {
        Thread thread = new Thread(() -> {
            while (true) {
                consumeFood(0.4f);
                consumeWater(0.8f);
                consumeSleep(0.7f);
                try {
                    Thread.sleep(60000); // wait for 1 minute
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private synchronized void consumeFood(float amount) {
        if (food >= amount) {
            food -= amount;
        } else {
            System.out.println("Not enough food!");
        }
    }

    private synchronized void consumeWater(float amount) {
        if (water >= amount) {
            water -= amount;
        } else {
            System.out.println("Not enough water!");
        }
    }

    private synchronized void consumeSleep(float amount) {
        if (sleep >= amount) {
            sleep -= amount;
        } else {
            System.out.println("Not enough sleep!");
        }
    }
}
