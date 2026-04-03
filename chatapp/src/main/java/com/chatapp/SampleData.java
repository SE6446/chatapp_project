package com.chatapp;

import java.util.*;

import com.chatapp.baseClasses.*;

public class SampleData {
    static App app = new App();
    static SelfSortedChatList<UUID, Chat> sampleChats = new SelfSortedChatList<>();
    static PersonalProfile sampleProfile = new PersonalProfile("Ron Sample", 500);
    static HashMap<UUID, Contact> sampleContacts = new HashMap<>();
    public static void main(String[] args) {
        generateSampleContacts();
        generateSampleChats();
        app.setChats(sampleChats);
        app.setProfile(sampleProfile);
        app.setContacts(sampleContacts);
        SaveState saveState = new SaveState(app);
        saveState.save("sampleData.bin");        
    }

    static void generateSampleChats() {
        UUID[] contactIds = sampleContacts.keySet().toArray(new UUID[0]);
        contactIds = Arrays.copyOf(contactIds, contactIds.length + 1);
        contactIds[contactIds.length - 1] = UUID.randomUUID();
        for (int i = 0; i < contactIds.length -1; i++) {
            Chat chat = new Chat(sampleContacts.get(contactIds[i]));
            switch (i) {
                case 0:
                    chat.sendMessage(new Message("I NEED vicodin [insert lightly racist/misogynistic quip]", sampleContacts.get(contactIds[i])));
                    break;
                case 1:
                    chat.sendMessage(new Message("600 hours clinic duty", sampleContacts.get(contactIds[i])));
                    break;
                case 2:
                    chat.sendMessage(new Message("I too am in this episode", sampleContacts.get(contactIds[i])));
            
                default:
                    break;
            }
            sampleChats.put(contactIds[i], chat);
        }
        Chat groupChat = new Chat(sampleContacts.values());
        Message[] messages = {new Message("I NEED Vicoding Cuddy",sampleContacts.get(contactIds[1])), new Message("No, 6 billion years clinic duty",sampleContacts.get(contactIds[2])), new Message("I too am in this conversation",sampleContacts.get(contactIds[0]))};
        sampleChats.put(contactIds[contactIds.length - 1], groupChat);
    }

    static void generateSampleContacts(){
        UUID[] ids = new UUID[3]; // 3 contacts
        String[] names = {"Dr. Wilson", "Greg House", "Cuddy"};
        int[] numbers = {200, 600, 700};
        for (int i = 0; i < ids.length; i++) {
            ids[i] = UUID.randomUUID();
        }
        for (int i = 0; i < ids.length; i++) {
            Contact contact = new Contact(names[i],numbers[i], ids[i]);
            sampleContacts.put(ids[i], contact);
        }
    }


}
