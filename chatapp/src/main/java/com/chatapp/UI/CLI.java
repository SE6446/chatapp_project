package com.chatapp.UI;

import java.util.Scanner;
//import java.util.UUID;

import com.chatapp.App;
import com.chatapp.baseClasses.*;


/**
 * Perhaps I should iterate that the UI elements should NOT use methods from base classes where possible and instead call from app.
 */

public class CLI {
    static App app;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        if (args.length == 0){
            app = new App();
            init(); // we only init if we're not loading from a save
        } else if (args.length == 1){
            app = new App(args[0]); //Input filename
        }
        landingPage();

    }
    static void init(){    
        System.out.println("Please enter the name for your profile:\n");
        String name = scanner.nextLine();
        System.out.println("Please enter the phone number for your profile:\n");
        int number = scanner.nextInt();
        app.initAccount(name, number);
        
    }
    static void landingPage(){
        while (true){
            System.out.println("Please enter the number for the command you wish to do.");
            System.out.println("1 - Edit Profile\n2 - Add Contact\n3 - create (group) chat\n4 - view chats");
            int answer = scanner.nextInt();
            
            switch (answer) {
                case 1:
                    System.out.println("What do you wish to edit?");
                    System.out.println("1 - Name\n2 - Phone Number");
                    answer = scanner.nextInt();

                    if (answer == 1){
                        System.out.println("What do you want your new name to be?");
                        scanner.nextLine(); // to eat the empty line
                        String reply = scanner.nextLine();
                        app.editProfile(reply, -1);
                    }
                    else if (answer == 2){
                        System.out.println("What do you want your new number to be?");
                        answer = scanner.nextInt();
                        scanner.nextLine();
                        app.editProfile(null, answer);
                    }
                    else{
                        System.out.println("That was not a valid option, please try again.");
                    }
                    
                    break;
                    
                case 2:
                    System.out.println("Input the name of the contact: ");
                    String name = scanner.nextLine();
                    System.out.println("Input their number (no spaces)");
                    String number = scanner.nextLine();
                    number = number.replaceAll("\\s","");
                    app.addContact(name, Integer.parseInt(number));
                    break;
                
                case 3:
                    // create group chat
                    break;

                case 4:
                    Chat selectedChat;
                    System.out.println(app.getChats());
                    System.out.println("Please enter the phone number of the user that you want to see that chat with: ");
                    scanner.nextLine(); // to eat the empty line
                    String reply = scanner.nextLine();
                    Contact contact = app.getContactFromNumber(Integer.parseInt(reply));

                    //try {
                    //    UUID.fromString(reply);
                    //} catch (Exception e) {
                    //    System.out.println("You have not entered a valid ID, please try something else.");
                    //    break;
                    //}

                    
                    selectedChat = app.getChatFromContact(contact);
                    if (selectedChat == null) {
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
