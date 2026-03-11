package com.chatapp.baseClasses;
import java.time.Instant; // for times
import java.util.Scanner; // for user inputs
import java.io.*; // for files

public class Message {
    Scanner s = new Scanner(System.in);
    public boolean sentByCurrentUser; // this can be used to decide which side of the screen chats appear on or to attribute it to the correct user 
    public String senderName; // not yet possible to set;
    public String text;
    public Instant timeSent;
    public Instant timeRecieved; // this must be set later in development as it is not yet possible to send messages
    public boolean readByOtherUser; // this must be set later in development as it is not yet possible to send messages
    public File messageAttachment; // could be an image or any other file, images will be displayed differently from other file types


    public Message(){
        text = "Message has failed to load correctly and was only set in the constructor.";
        timeSent = Instant.now();
        readByOtherUser = false;
        senderName = "Placeholder"; // This can be set once profiles have been created and the current user can be seen.
        
    }

    public Message(String textInput){
        text = textInput;
        timeSent = Instant.now();
    }

    // Makes user-inputted changes the text variable of the object.
    public void edit(){
        boolean validated = false;
        String userInput = "Default Edit Message"; // called this for debugging

        while (!validated) {
            System.out.println("Please enter the new line you want to replace the old message with:\nYou can also cancel the edit by inputting \"!CANCEL\"\n");
            userInput = s.nextLine();

            if (userInput.equals("!CANCEL") || userInput.equals("!cancel") || userInput.equals("!Cancel")){
                System.out.println("Edit Canceled");
                return;
            }

            System.out.println("Your edited message is \"" + userInput + "\", is this correct? Y/N: ");
            String validationInput = s.nextLine();

            if (validationInput.equals("Y") || validationInput.equals("y")){
                System.out.println("\nEdit Accepted.");
                validated = true;
            }
            else if (validationInput.equals("N") || validationInput.equals("n")){
                System.out.println("\nEdit Aborted, Please Try Again.");
            }
            else{
                System.out.println("You did not enter a valid option. Please try again.");
            }
        }

        text = userInput;
    }
}