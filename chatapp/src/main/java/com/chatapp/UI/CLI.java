package com.chatapp.UI;

import java.util.Scanner;

import com.chatapp.App;

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


    }
    static void init(){
        if(app.getProfile().equals(null)){
            //TODO Add initalisation code
        }
    }
    static void landingPage(){
        

    }
    static void addContact(){
        String nameString = scanner.nextLine();
        int number = Integer.parseInt(scanner.nextLine());
        app.addContact(nameString, number);
    }

}
