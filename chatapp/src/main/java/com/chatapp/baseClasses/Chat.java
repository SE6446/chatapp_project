package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;

public class Chat implements Serializable{

    private LinkedList<Message> chat;
    private ArrayList<Profile> members;
    public Scanner s =  new Scanner(System.in);

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

    public boolean deleteMessage(Message message){
        boolean confirmed = false;

        while (!confirmed){
            //using system.out just to get the logic there before using swing
            System.out.println("Are you sure you want to delete this message?");
            String result = s.nextLine();

            if (result.equalsIgnoreCase("yes")){
                chat.remove(message);
                confirmed = true;

            }else if (result.equalsIgnoreCase("no")){
                return false;
            }
        }
        return true;
    }

    //Adding editing from Message class.


}
