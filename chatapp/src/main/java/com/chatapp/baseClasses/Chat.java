package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ArrayList;

public class Chat implements Serializable{

    private LinkedList<Message> chat;
    private ArrayList<Profile> members;
    private Message front;

    public Chat() {
        chat = new LinkedList<>();
        front = chat.getFirst() ;
    }

    public LinkedList<Message> getChat() {
        return chat;
    }

    public LinkedList<Message> displayChat() {

    }

    public Message sendMessage(Message message){
        chat.addFirst(message);
        return message;
    }

    public Message deleteMessage(Message message){
        chat.remove(message);
        return message;
    }


}
