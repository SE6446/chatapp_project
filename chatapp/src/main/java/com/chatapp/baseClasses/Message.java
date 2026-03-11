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

    // Debugging methods below:

    private void display(){
        System.out.println(text);
    }

    //public static void main(String[] args) {
    //    Message m = new Message("Hello I Am A Message");
    //    m.display();
    //    m.edit();
    //    m.display();
    //}
}