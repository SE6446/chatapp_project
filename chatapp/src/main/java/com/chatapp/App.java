package com.chatapp;
import com.chatapp.baseClasses.*;

import java.util.HashMap;
import java.util.UUID;


public class App {
    // A tree could be used here but it's more complex for not much gain as we're not building a proper database.
    // As UUIDs are essentially random and have no innate ordering. But if we used a simpler identifier than maybe.
    // Let me know what you think - Archie
    private HashMap<UUID, Chat> chats;
    public HashMap<UUID, Chat> getChats() {
        return chats;
    }

    private HashMap<UUID, Contact> contacts;
    public HashMap<UUID, Contact> getContacts() {
        return contacts;
    }

    private Profile profile;
    
    
    public Profile getProfile() {
        return profile;
    }
    
    public App(String fileName){
        SaveState saveState = new SaveState(fileName);
        chats = saveState.getChats();
        contacts = saveState.getContacts();
        profile = saveState.getProfile();
    }

    public App(SaveState saveState) throws Exception{
        throw new Exception("Not Implemented");
    }

    public App(){
        // Keep empty for default init.
    }

    // TODO implement
    // Do so by adding methods that the UIs would need. Such as a specific chat from an ID.
}
