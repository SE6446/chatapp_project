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
            sampleChats.put(contactIds[i], chat);
        }
        Chat groupChat = new Chat(sampleContacts.values());
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
