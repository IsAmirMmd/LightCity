package org.example.models;

import org.example.defualtSystem.Municipality;

import java.util.Random;

public class Property {
    private float[] scales;
    private float[] coordinate;
    private Character owner;
    private Random random = new Random();
    private int id;
    private boolean ForSale = true;
    private float price;

    public Property(float[] scales, float[] coordinate, Character owner) {
        this.id = 000000;
        this.scales = scales;
        this.coordinate = coordinate;
        this.owner = owner;
        this.price = 4.5f;
    }

    public boolean isForSale() {
        return ForSale;
    }

    public void setForSale(boolean forSale) {
        ForSale = forSale;
    }

    public float[] getScales() {
        return scales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScales(float[] scales) {
        this.scales = scales;
    }

    public Character getOwner() {
        return owner;
    }

    public void setOwner(Character owner) {
        this.owner = owner;
    }

    public float[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(float[] coordinate) {
        this.coordinate = coordinate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
