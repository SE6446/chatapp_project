package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonalProfile implements Profile, Serializable {
    private int phoneNumberID;

    private String name;

    private UUID uuid;

    private ArrayList<Contact> contactsList;

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

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public PersonalProfile(String name, int number){
        phoneNumberID = number;
        this.name = name;
        uuid = UUID.randomUUID();
    }

    public void addContact(Contact contact){
        contactsList.add(contact);
    }

    public void addContact(Scanner s){ // Manual Version
        // creating a new contact
        Contact contact = new Contact();

        // set values
        System.out.println("Please enter the name of the contact:\n");
        contact.setHandle(s.nextLine());
        System.out.println("Please enter the phone number of the contact:\n");
        contact.setphoneNumber(s.nextInt());

        // add to the contact list
        contactsList.add(contact);
    }

}
