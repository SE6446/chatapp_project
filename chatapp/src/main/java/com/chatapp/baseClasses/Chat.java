package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;

public class Chat implements Serializable{

    private LinkedList<Message> chat;
    private ArrayList<Profile> members;
    private Profile host;
    private boolean groupChat;
    public Scanner s =  new Scanner(System.in);

    public Chat() {
        chat = new LinkedList<>();
        members = new ArrayList<>() ;
        groupChat = false;
        host = null;
    }

    public LinkedList<Message> getChat() {
        return chat;
    }

    public void displayChat() {
        for (Message m : chat) {
            System.out.println(m.getText());
        }
    }

    public ArrayList<Profile> getMembers() {
        return members;
    }

    public void addMember(Profile p) {
        members.add(p);
    }

    public void kickMember(Profile kicker, Profile kicked) {
        if (groupChat) {

            if (kicker != host) {
                System.out.println("Permission Denied: You are not the host of this chat.");
                return;
            } else {
                members.remove(kicked);
            }

        } else {
            System.out.println("You cannot kick a member from a private chat.");
        }
    }

    public Message sendMessage(Message message){ // .addFirst so that the most recent message will always appear at screen bottom
        chat.addFirst(message);
        return message;
    }

    public boolean deleteMessage(Message message){
        //will add function to ensure messages can't be deleted by users that aren't the sender.
        boolean confirmed = false;

        while (!confirmed){
            //using system.out just to get the logic there before using swing
            System.out.println("Are you sure you want to delete this message? :\n" + "'"+message.getText()+"'" );
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
