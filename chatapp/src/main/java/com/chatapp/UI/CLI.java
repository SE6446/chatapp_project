package com.chatapp.UI;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.UUID;
//import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
            System.out.println("Current feed");
            Stack<Chat> feed = app.getFeed();
            int limit;
            if (feed.size() <= 3) {
                limit = feed.size();
            } else {
                limit = 3;
            }
            for (int i = 0; i < limit; i++) {
                System.out.println(feed.pop());
            }
            System.out.println("Please enter the number for the command you wish to do.");
            System.out.println("1 - Edit Profile\n2 - Add Contact\n3 - create (group) chat\n4 - view chats\n5 - Group Chats\n6 - View Contact\n7- Load from file\n8- save to file");
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
                    scanner.nextLine(); // eat the empty line
                    System.out.println("Input the name of the contact: ");
                    String name = scanner.nextLine();
                    System.out.println("Input their number (no spaces)");
                    String number = scanner.nextLine();
                    number = number.replaceAll("\\s","");
                    app.addContact(name, Integer.parseInt(number));
                    break;
                
                case 3:
                    // create group chat
                    scanner.nextLine(); //eats the empty line
                    System.out.println("Input the phone numbers to include (comma seperated): ");
                    String numbersString = scanner.nextLine();
                    String[] numbers = numbersString.split(",");
                    ArrayList<Contact> contacts = new ArrayList<>();
                    for (String groupNumber : numbers) {
                        Contact contact = app.getContactFromNumber(Integer.parseInt(groupNumber));
                        contacts.add(contact);
                    }
                    app.createGroupChat(contacts);
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
                    chatPage(selectedChat);
                    break;
                
                case 5:
                    // Group chats
                    int counter = 0;
                    for (Chat values : app.getGroupChats().values()){
                        counter++;
                        System.out.println("Group Chat " + counter + ": " + values);
                        scanner.nextLine(); // eats empty line
                        System.out.println("Is this the group chat you want? Y/N:");
                        String qAnswer = scanner.nextLine();

                        if (qAnswer.equals("Y") || qAnswer.equals("y")){
                            chatPage(values);
                        }
                        else{
                            System.out.println("Either N was chosen or option was invalid");
                        }
                    }
                    break;
                case 6:
                    // view contact
                    System.out.println("Please enter the phone number of the user that you want to see that chat with: ");
                    scanner.nextLine(); // to eat the empty line
                    reply = scanner.nextLine();
                    contact = app.getContactFromNumber(Integer.parseInt(reply));
                    System.out.println("Contact: "+contact);
                    System.out.println("Most Recent Messages:\n" + app.getMostRecentMessages(contact));
                    break;
                case 7:
                    System.out.println("Please input the filename of the file you're trying to load");
                    scanner.nextLine();
                    reply = scanner.nextLine();
                    load(reply);
                case 8:
                    System.out.println("Please input the filename of the file you're trying to load");
                    scanner.nextLine();
                    reply = scanner.nextLine();
                    save(reply);
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

    static void chatPage(Chat chat){
        while (true) {
            flushCli();
            chat.displayChat();
            System.out.println("Write something to send, press enter with no text to exit bact to main menu:\n");
            String messageString = scanner.nextLine();
            if (messageString != "") {
                Message message = new Message(messageString, app.getProfile());
                chat.sendMessage(message);
            } else {
                return; // This is the only exit condition.
            }            
        }
    }

    protected static void flushCli(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void save(String fileName){
        System.out.println("Saved to "+ fileName); // I won't add extension shenanigans, be responsible
        SaveState saveState = new SaveState(app);
        saveState.save(fileName);
    }

    static void load(String fileName){
        app = new App(fileName);
    }

    

}
