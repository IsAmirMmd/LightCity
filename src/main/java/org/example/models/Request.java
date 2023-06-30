package org.example.models;

public class Request {
    private int id = 0;
    private String oldOwner = " ";
    private String newOwner = " ";
    private String coordinates = " ";

    public Request(String oldOwner, String newOwner, String coordinates) {
        this.oldOwner = oldOwner;
        this.newOwner = newOwner;
        this.coordinates = coordinates;
        this.id = 0;
    }

    public String getOldOwner() {
        return oldOwner;
    }

    public void setOldOwner(String oldOwner) {
        this.oldOwner = oldOwner;
    }

    public String getNewOwner() {
        return newOwner;
    }

    public void setNewOwner(String newOwner) {
        this.newOwner = newOwner;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
