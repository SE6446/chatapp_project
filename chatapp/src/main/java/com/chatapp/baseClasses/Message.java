package com.chatapp.baseClasses;
import java.time.Instant; // for times
import java.util.Scanner; // for user inputs
import java.util.UUID;
import java.io.*; // for files

public class Message {
    Scanner s = new Scanner(System.in);
    private boolean sentByCurrentUser; // this can be used to decide which side of the screen messages appear on or to attribute it to the correct user 
    private boolean readByOtherUser; // this must be set later in development as it is not yet possible to send messages
    private String senderName; // not yet possible to set;
    private String text;
    private Instant timeSent;
    private Instant timeRecieved; // this must be set later in development as it is not yet possible to send messages
    private File messageAttachment; // could be an image or any other file, images will be displayed differently from other file types in a possible GUI
    private UUID userUUID;
    
    

    public UUID getuserUUID() {
        return userUUID;
    }

    public Message(){
        text = "Message has failed to load correctly and/or was only set in the constructor.";
        timeSent = Instant.now();
        readByOtherUser = false;
        senderName = "Placeholder"; // This can be set once profiles have been created and the current user can be seen.
        
    }

    public Message(String textInput){
        text = textInput;
        timeSent = Instant.now();
        readByOtherUser = false;
        senderName = "Placeholder"; // This can be set once profiles have been created and the current user can be seen.
    }

    // Makes user-inputted changes the text variable of the object.
    public void edit(){
        boolean validated = false;
        String userInput = "Default Edit Message"; // initially called this for debugging

        while (!validated) {
            System.out.println("Please enter the new line you want to replace the old message with:\nYou can also cancel the edit by inputting \"!CANCEL\"\n");
            userInput = s.nextLine();

            // allowing the user to back out of an edit in case of mistakes when choosing the command that they want
            if (userInput.equals("!CANCEL") || userInput.equals("!cancel") || userInput.equals("!Cancel")){
                System.out.println("Edit Canceled");
                return;
            }

            // ensure the user got the result that they intended
            System.out.println("Your edited message is \"" + userInput + "\", is this correct? Y/N: ");
            String validationInput = s.nextLine();

            if (validationInput.equals("Y") || validationInput.equals("y")){
                System.out.println("\nEdit Accepted.");
                validated = true;
            }
            else if (validationInput.equals("N") || validationInput.equals("n")){
                // makes the user input a new edited message
                System.out.println("\nEdit Aborted, Please Try Again.");
            }
            else{
                System.out.println("You did not enter a valid option. Please try again.");
                // possible improvement here is to create a smaller loop so that the user does not have to input the edited message again but rather just the confirmation 
            }
        }

        text = userInput;
    }

    // Get and Set Methods:

    public boolean getSentByCurrentUser(){
        return sentByCurrentUser;
    }

    public void setSentByCurrentUser(boolean newValue){
        sentByCurrentUser = newValue;
    }

    public boolean getReadByOtherUser(){
        return readByOtherUser;
    }

    public void setReadByOtherUser(boolean newValue){
        readByOtherUser = newValue;
    }

    public String getSenderName(){
        return senderName;
    }

    public void setSenderName(String newValue){
        senderName = newValue;
    }

    public String getText(){
        return text;
    }

    public void setText(String newValue){
        text = newValue;
    }

    public File getAttachment(){
        return messageAttachment;
    }

    public void setAttachment(File newFile){
        messageAttachment = newFile;
    }

    public Instant getTimeSent(){
        return timeSent;
    }

    public Instant getTimeRecieved(){
        return timeRecieved;
    }

    public void setTimeRecieved(Instant newInstant){
        timeRecieved = newInstant;
    }
    
}