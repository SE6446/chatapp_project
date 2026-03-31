package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.UUID;

public class Contact implements Profile, Serializable {

    private int phoneNumberID;

    private String name;

    private UUID uuid;

    @Override
    public int getphoneNumber() {
        return phoneNumberID;
    }

    public void setphoneNumber(int newphoneNumber){
        phoneNumberID = newphoneNumber;
    }

    @Override
    public String getHandle() {
        return name;
    }

    public void setHandle(String handle){
        name = handle;
    }

    public Contact(String name, int number, UUID inputUuid){
        phoneNumberID = number;
        this.name = name;
        // In a networking scenario we would get this from the server.
        uuid = inputUuid;
    }

    public Contact(){
        phoneNumberID = 000000000;
        this.name = "Placeholder Name";
        uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nPhone Number: " + this.phoneNumberID;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
