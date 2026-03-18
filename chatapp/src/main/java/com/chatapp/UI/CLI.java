package com.chatapp.UI;

import java.util.Scanner;
import java.util.UUID;

import com.chatapp.App;
import com.chatapp.baseClasses.Chat;
import com.chatapp.baseClasses.PersonalProfile;

public class CLI {
    static App app;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        if (args.length == 0){
            app = new App();
        } else if (args.length == 1){
            app = new App(args[0]);
        }
        init();
        landingPage();

    }
    static void init(){
        if(app.getProfile() == null){
            System.out.println("Please enter the name for your profile:\n");
            String name = scanner.nextLine();
            System.out.println("Please enter the phone number for your profile:\n");
            int number = scanner.nextInt();
            app.initAccount(name, number);
        }
    }
    static void landingPage(){
        while (true){
            System.out.println("Please enter the number for the command you wish to do.");
            System.out.println("1 - Edit Profile\n2 - Add Contact\n3 - create chat\n4 - view chats");
            int answer = scanner.nextInt();
            
            switch (answer) {
                case 1:
                    System.out.println("What do you wish to edit?");
                    System.out.println("1 - Name\n2 - Phone Number");
                    answer = scanner.nextInt();

                    if (answer == 1){
                        // name editing stuff here
                    }
                    else if (answer == 2){
                        // phone num editing stuff goes here
                    }
                    else{
                        System.out.println("That was not a valid option, please try again.");
                    }
                    break;
                    
                case 2:
                    app.getProfile().addContact(scanner); // may want to do a different way later
                    break;
                
                case 3:
                    // create chats /////////////////////////////////////
                    break;

                case 4:
                    Chat selectedChat;
                    app.getProfile().displayContacts();
                    System.out.println("Please enter the ID of the user that you want to see that chat with: ");
                    scanner.nextLine(); // to eat the empty line
                    String reply = scanner.nextLine();
                    try {
                        UUID.fromString(reply);
                    } catch (Exception e) {
                        System.out.println("You have not entered a valid ID, please try something else.");
                        break;
                    }

                    try {
                        selectedChat = app.getChat(UUID.fromString(reply));
                    } catch (Exception e) {
                        System.out.println("You have no chats with this contact.");
                        break;
                    }

                    selectedChat.displayChat();
                    
                    break;
                default:
                    System.out.println("That was not a valid option, please try again.");
                    break;
                }
        }

    }
    static void addContact(){
        String nameString = scanner.nextLine();
        int number = Integer.parseInt(scanner.nextLine());
        app.addContact(nameString, number);
    }

}
