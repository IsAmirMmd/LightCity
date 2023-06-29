package org.example.models;

public class Request {
    private String oldOwner = " ";
    private String newOwner = " ";
    private String coordinates = " ";

    public Request(String oldOwner, String newOwner, String coordinates) {
        this.oldOwner = oldOwner;
        this.newOwner = newOwner;
        this.coordinates = coordinates;
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
}
