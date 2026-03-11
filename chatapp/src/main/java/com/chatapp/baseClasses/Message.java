package com.chatapp.baseClasses;
import java.time.Instant;
import java.util.Scanner;

public class Message {
    Scanner s = new Scanner(System.in);
    public boolean sentByCurrentUser; // this can be used to decide which side of the screen chats appear on or to attribute it to the correct user 
    public String text;
    public Instant timeSent;


    public Message(){
        text = "Message has failed to load correctly and was only set in the constructor.";
        timeSent = Instant.now();
    }

    public Message(String textInput){
        text = textInput;
        timeSent = Instant.now();
    }

    public void edit(){
        boolean validated = false;
        String userInput = "Default Edit Message"; // called this for debugging

        while (!validated) {
            System.out.println("Please enter the new line you want to replace the old message with:\nYou can also cancel the edit by inputting \"!CANCEL\"\n");
            userInput = s.nextLine();

            if (userInput.equals("!CANCEL") || userInput.equals("!cancel")){
                System.out.println("Edit Canceled");
                return;
            }

            System.out.println("Your edited message is \"" + userInput + "\", is this correct? Y/N: ");
            String validationInput = s.nextLine();

            if (validationInput.equals("Y")){
                System.out.println("\nEdit Accepted.");
                validated = true;
            }
            else if (validationInput.equals("N")){
                System.out.println("\nEdit Aborted, Please Try Again.");
            }
            else{
                System.out.println("You did not enter a valid option. Please try again.");
            }
        }
        
        text = userInput;
    }
}