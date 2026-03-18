package com.chatapp;
import com.chatapp.baseClasses.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;
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

    private PersonalProfile profile;
    
    
    public PersonalProfile getProfile() {
        return profile;
    }

    public void initAccount(String name, int number){
        profile = new PersonalProfile(name, number);
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

    public Chat getChat(UUID id){
        return chats.get(id);
    }

    public UUID createChat(Collection<? extends Contact> contacts){
        Chat newChat = new Chat(contacts, profile);
        UUID id = UUID.randomUUID();
        chats.put(id, newChat);
        return id;
    }

    public UUID addChat(Chat chat){
        UUID id = UUID.randomUUID();
        chats.put(id, chat);
        return id;
    }

    public Contact getContact(UUID id){
        return contacts.get(id);
    }

    public void addContact(String name, int number){
        UUID uuid = UUID.randomUUID();
        Contact contact = new Contact(name, number);
        contacts.put(uuid, contact);
    }

    public Contact getContactFromNumber(int number){
        ArrayList<Contact> contactValues = (ArrayList<Contact>) contacts.values(); //What the fuck
        for (Contact contact : contactValues) {
            if (contact.getphoneNumber() == number) {
                return contact;
            }
        }
        return null;

    }

    public Stack<Chat> getFeed(){
        //TODO implement
        throw new UnsupportedOperationException("Not Implemented");
    }
    public Message searchMessage(String keywordString){
        //TODO implement
        throw new UnsupportedOperationException("Not Implemented");
    }
}
