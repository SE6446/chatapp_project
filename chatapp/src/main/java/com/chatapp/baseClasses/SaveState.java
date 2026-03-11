package com.chatapp.baseClasses;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SaveState implements Serializable{
    private ArrayList<Contact> contacts;
    private ArrayList<Chat> chats;
    private Profile profile;
    public SaveState(String fileName){
        try {
            FileInputStream saveInput = new FileInputStream(fileName);
            ObjectInputStream saveObjectInput = new ObjectInputStream(saveInput);
            Object save = saveObjectInput.readObject();
            saveObjectInput.close();
            if (save instanceof SaveState) {
                SaveState state = (SaveState) save;
                transfer(state);
                
            } else {
                throw new Exception("Save corrupted!");
            }

        } catch (Exception e) {
            System.err.println("An Error occured");
            e.printStackTrace();
            
        }
    }

    protected void transfer(SaveState state){
        this.chats = state.getChats();
        this.contacts = state.getContacts();
        this.profile = state.getProfile();
    }
    public void save(String fileName){
        try {
            FileOutputStream saveFileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream saveObjectOutputStream = new ObjectOutputStream(saveFileOutputStream);
            saveObjectOutputStream.writeObject(this);
            saveObjectOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occured!");
        }
    }
    public ArrayList<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }
    public ArrayList<Chat> getChats() {
        return chats;
    }
    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }
    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
}
