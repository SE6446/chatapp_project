package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ArrayList;

public class Chat implements Serializable{

    private LinkedList<Message> chat;
    private ArrayList<Profile> members;

    public Chat() {
        chat = new LinkedList<>();
        members = new ArrayList<>() ;
    }

    public LinkedList<Message> getChat() {
        return chat;
    }

    public LinkedList<Message> displayChat() {
        // to display chat from most recent to earliest- can maybe do .toArray()
    }

    public Message sendMessage(Message message){
        chat.addFirst(message);
        return message;
    }

    public Message deleteMessage(Message message){
        chat.remove(message);
        return message;
    }

    //Adding editing from Message class.


}
