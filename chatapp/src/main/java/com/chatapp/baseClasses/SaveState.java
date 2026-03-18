package com.chatapp.baseClasses;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import com.chatapp.App;

public class SaveState implements Serializable{
    private HashMap<UUID,Contact> contacts;
    private HashMap<UUID,Chat> chats;
    private PersonalProfile profile;
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
    public SaveState(App app){
        transfer(app);
    }

    protected void transfer(App state){
        this.chats = state.getChats();
        this.contacts = state.getContacts();
        this.profile = state.getProfile();
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
    public HashMap<UUID,Contact> getContacts() {
        return contacts;
    }
    public void setContacts(HashMap<UUID,Contact> contacts) {
        this.contacts = contacts;
    }
    public HashMap<UUID,Chat> getChats() {
        return chats;
    }
    public void setChats(HashMap<UUID,Chat> chats) {
        this.chats = chats;
    }
    public PersonalProfile getProfile() {
        return profile;
    }
    public void setProfile(PersonalProfile profile) {
        this.profile = profile;
    }
    
}
