package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.UUID;

public class PersonalProfile implements Profile, Serializable {
    private int phoneNumberID;

    private String name;

    private UUID uuid;

    //private ArrayList<Contact> contactsList;

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

    public void setPhoneNumberID(int phoneNumberID) {
        this.phoneNumberID = phoneNumberID;
    }
    
    @Override
    public UUID getUUID() {
        return uuid;
    }

    public PersonalProfile(String name, int number){
        phoneNumberID = number;
        this.name = name;
        uuid = UUID.randomUUID();
        //contactsList = new ArrayList<Contact>();
    }

}
