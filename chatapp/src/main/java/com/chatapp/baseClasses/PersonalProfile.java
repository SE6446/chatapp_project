package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.UUID;

public class PersonalProfile implements Profile, Serializable {
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

    public PersonalProfile(String name, int number){
        phoneNumberID = number;
        this.name = name;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }
}
