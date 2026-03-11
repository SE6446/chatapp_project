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
    }
    
}
