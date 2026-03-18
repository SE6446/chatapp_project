package com.chatapp;
import com.chatapp.baseClasses.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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

    public App(SaveState saveState) throws Exception {
        chats = saveState.getChats();
        contacts = saveState.getContacts();
        profile = saveState.getProfile();
    }

    public App(){
        // Keep empty for default init.
    }

    // TODO implement
    // Do so by adding methods that the UIs would need. Such as a specific chat from an ID.

    public Chat getChat(UUID id){
        return chats.get(id);
    }

    public UUID createGroupChat(Collection<Contact> contacts){
        Chat newChat = new Chat(contacts);
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

    public UUID addContact(String name, int number){
        UUID uuid = UUID.randomUUID();
        Contact contact = new Contact(name, number, uuid);
        contacts.put(uuid, contact);
        Chat p2pChat = new Chat(contact);
        chats.put(uuid, p2pChat); // Make this the same id for easy searching
        return uuid;
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

    public Chat getChatFromContact(Contact contact){
        UUID uuid = contact.getUUID();
        return chats.get(uuid);
    }
    
    public HashMap<UUID, Chat> getGroupChats(){
        HashMap<UUID, Chat> groupChats = new HashMap<>();
        for (UUID id : chats.keySet()) {
            if (chats.get(id).isHost()) {
                groupChats.put(id, chats.get(id));
            }
        }
        return groupChats;
    }

    public Stack<Chat> getFeed(){
        //TODO implement
        throw new UnsupportedOperationException("Not Implemented");
    }
    public Message searchMessage(String keywordString, Chat chat){
        //TODO implement
        throw new UnsupportedOperationException("Not Implemented");
    }

    public void save(String fileString) throws Exception {
        SaveState saveState = new SaveState(this);
        try {
            FileOutputStream saveFileOutputStream = new FileOutputStream(fileString + ".bin");
            ObjectOutputStream saveObjectOutputStream = new ObjectOutputStream(saveFileOutputStream);
            saveObjectOutputStream.writeObject(saveState);
            saveObjectOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occured!");
        }
    }
    
    /**
     * Edit the personal profile of the user, to not edit name, input null, to not edit phone number input -1
     * @param name String
     * @param phoneNumberID int
     */
    public void editProfile(String name, int phoneNumberID){
        if (name != null) {
            profile.setHandle(name);
        }
        if (phoneNumberID != -1) {
            profile.setPhoneNumberID(phoneNumberID);
        }
    }
}
