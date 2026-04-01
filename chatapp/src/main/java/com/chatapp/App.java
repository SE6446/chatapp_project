package com.chatapp;
import com.chatapp.baseClasses.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;


public class App {
    //* */ A tree could be used here but it's more complex for not much gain as we're not building a proper database.
    //* */ As UUIDs are essentially random and have no innate ordering. But if we used a simpler identifier than maybe.
    //* */ Let me know what you think - Archie
    private SelfSortedChatList<UUID, Chat> chats;
    public void setChats(SelfSortedChatList<UUID, Chat> chats) {
        this.chats = chats;
    }

    public SelfSortedChatList<UUID, Chat> getChats() {
        return chats;
    }

    private HashMap<UUID, Contact> contacts;
    public void setContacts(HashMap<UUID, Contact> contacts) {
        this.contacts = contacts;
    }

    public HashMap<UUID, Contact> getContacts() {
        return contacts;
    }

    private PersonalProfile profile;
    
    
    public void setProfile(PersonalProfile profile) {
        this.profile = profile;
    }

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
        chats = new SelfSortedChatList<>();
        contacts = new HashMap<UUID, Contact>();
        profile = new PersonalProfile();
    }

    // TODO implement
    // Do so by adding methods that the UIs would need. Such as a specific chat from an ID.

    public Chat getChat(UUID id){
        return chats.get(id);
    }

    public UUID createGroupChat(Collection<Contact> contacts){
        Chat newChat = new Chat(contacts);
        newChat.setGroupChatStatus(true);
        UUID id = UUID.randomUUID();
        chats.put(id, newChat);
        return id;
    }

    public UUID addChat(Chat chat){
        UUID id = UUID.randomUUID();
        chats.put(id, chat);
        return id;
    }

    public void addChatWithContact(Chat chat, Contact contact){
        chats.put(contact.getUUID(), chat);
    }

    public Contact getContact(UUID id){
        return contacts.get(id);
    }

    /**
     * Adds a new Contact, AND creates a corresponding chat.
     * @param name
     * @param number
     * @return the UUID of the contact/chat
     */
    public UUID addContact(String name, int number){
        UUID uuid = UUID.randomUUID();
        Contact contact = new Contact(name, number, uuid);
        contacts.put(uuid, contact);
        Chat p2pChat = new Chat(contact);
        chats.put(uuid, p2pChat); // Make this the same id for easy searching
        return uuid;
    }

    public Contact getContactFromNumber(int number){
        Iterator<Map.Entry<UUID,Contact>> it = contacts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, Contact> me = it.next();
            if (me.getValue().getphoneNumber() == number) {
                return me.getValue();
            }
        }
        
        return null;

    }
    /**
     * @deprecated
     * @param number
     * @return
     */
    public Contact getContactFromNumber(String number){
        ArrayList<Contact> contactValues = (ArrayList<Contact>) contacts.values(); //What the fuck
        for (Contact contact : contactValues) {
            if (contact.getphoneNumber() == Integer.parseInt(number)) {
                return contact;
            }
        }
        return null;
    }

    public Chat getChatFromContact(Contact contact){
        UUID uuid = contact.getUUID();
        return chats.get(uuid);
    }
    
    public void deleteChat(Chat targetChat) {
        for (UUID id : chats.keySet()) {
            if (chats.get(id).equals(targetChat)) {
                  chats.remove(id);  
            }
        }
    }

    public void deleteContact(Contact targetContacts) {
        for (UUID id : contacts.keySet()) {
            if (contacts.get(id).equals(targetContacts)) {
                  contacts.remove(id);  
            }
        }
    }

    public HashMap<UUID, Chat> getGroupChats(){
        HashMap<UUID, Chat> groupChats = new HashMap<>();
        for (UUID id : chats.keySet()) {
            if (chats.get(id).isGroupChat()) {
                groupChats.put(id, chats.get(id));
            }
        }
        return groupChats;
    }

    public Stack<Chat> getFeed(){
        Stack<Chat> feed = new Stack<>();
        for (Chat chat : chats.values()) {
            feed.add(chat);
        }
        return feed;
    }
    public Stack<Message> searchForMessage(String keywordString, Chat chat){
        Stack<Message> matchedMessages = new Stack<>();
        Iterator<Message> chatIterator = chat.iterator();
        while (chatIterator.hasNext()) {
            Message itemMessage = chatIterator.next();
            if (itemMessage.toString().contains(keywordString)) {
                matchedMessages.add(itemMessage);
            }
        }
        return matchedMessages;
    }

    public HashSet<Message> getMostRecentMessages(Contact contact){
        HashSet<Message> messages = new HashSet<>();
        UUID uuid = contact.getUUID();
        //First we look for the DM
        Chat chat = getChat(uuid);
        messages.add(chat.getMostRecentMessageFromUUID(uuid));
        //Then we look around GCs
        for (Map.Entry<UUID,Chat> me : getGroupChatsWithContact(contact).entrySet()) {
            if(me.getValue().getMembers().contains(contact)){
                messages.add(me.getValue().getMostRecentMessageFromUUID(uuid));
            }
        }
        if (messages.size() == 0) {
            return null;
        } else {
            return messages;
        }
    }

    public HashMap<UUID, Chat> getGroupChatsWithContact(Contact contact){
        HashMap<UUID, Chat> groupChats = new HashMap<>();
        for (UUID id : chats.keySet()) {
            if (chats.get(id).isGroupChat() && chats.get(id).getMembers().contains(contact)) {
                groupChats.put(id, chats.get(id));
            }
        }
        return groupChats;
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
