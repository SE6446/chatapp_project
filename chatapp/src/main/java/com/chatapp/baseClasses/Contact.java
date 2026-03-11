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

    public Contact(String name, int number){
        phoneNumberID = number;
        this.name = name;
        uuid = UUID.randomUUID();
    }

    public Contact(){
        phoneNumberID = 000000000;
        this.name = "Placeholder Name";
        uuid = UUID.randomUUID();
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
