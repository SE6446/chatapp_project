package com.chatapp.baseClasses;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

public class Chat implements Serializable {
    private LinkedList<Message> chat;
    private ArrayList<Contact> members;
    private boolean isHost = false;
    private boolean groupChat;
    //public Scanner s =  new Scanner(System.in); // Scanner should be a dependancy injection from the UI.
    //As it would be different from the cli to the GUI.


    public Chat(Collection<Contact> chatMembers) {
        members.addAll(chatMembers);
        if (members.size() > 1){
            groupChat = true;
        }
        else {
            groupChat = false;
        }
        isHost = true; // If instatiating a class like this, we can assume the instantiator is the host.
    }

    public Chat(Contact contact){
        groupChat = false;
        isHost = true;
        members.add(contact);
    }

    /**
     * @deprecated What is this even for?
     */
    public void initialiseChat(){
        chat.clear();
        members.clear();
        //members.add(host); // As host is no longer a profile class, this cannot work
    }

    public LinkedList<Message> getChat() {
        return chat;
    }

    public void displayChat() {
        for (Message m : chat) {
            System.out.println(m.getText());
        }
    }

    public boolean isHost(){
        return isHost;
    }

    public boolean isGroupChat(){
        if (members.size() > 2) {
            groupChat = true;
            return true;
        }
        return false;
    }

    public ArrayList<? extends Profile> getMembers() {
        return members;
    }

    public void addMember(Contact p) {
        if (isHost) {
            members.add(p);
        }
    }

    public void kickMember(Profile kicker, Profile kicked) {
        if (groupChat) {

            if (!isHost) {
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

    public boolean deleteMessage(Message message, Scanner s){
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



    public Iterator<Message> iterator(){
        return chat.iterator();
    }
}