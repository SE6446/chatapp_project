package com.chatapp.networking;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

import io.fusionauth.http.*;
import io.fusionauth.http.server.HTTPHandler;

import org.json.*;

import com.chatapp.baseClasses.Chat;

public class Server {
    static HashMap<UUID,Chat> knownChats;
    static HashMap<UUID,String> ipMappingTable;
    static HashMap<String, Boolean> activeUserTable;
    final static String helpString = "Argument: <port-number>";
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Too many arguments detected!\n"+helpString);
        }
        HTTPHandler handler = (req,res) ->{
            String ip = req.getIPAddress();
            if (!req.hasBody()) {
                res.setStatus(400);
                res.close();
            }
            String body = req.getBodyBytes().toString();
            JSONObject jsonBody = new JSONObject(body);
            //There are four methods: declare (HEAD), update(POST), create(POST), disconnect (PUT) and fix desync(GET)
            if (req.getMethod().equals(HTTPMethod.HEAD)) {
                //This is a declare method, we do not need to respond with anything other than 200
                //body should look like this:
                // {uuid:"{uuid to string}", phone_number: "{number}"}
                // if a uuid is already present, we treat this as if they just changed their number.
                UUID uuidString = UUID.fromString(jsonBody.getString("uuid"));
                String phoneString = jsonBody.getString("phone_number"); //This is useless for now, utterly cosmetic
                if (ipMappingTable.containsKey(uuidString)){
                    //we just update the key.
                    ipMappingTable.replace(uuidString, req.getIPAddress());
                } else {
                    ipMappingTable.put(uuidString, req.getIPAddress());
                }
                res.setStatus(200);
                res.close();
            } else if (req.getMethod().equals(HTTPMethod.GET)) {
                //Here we basically update the user requesting the update on the true state of the chats that contain the user, it also adds them to the active users list.
                //If the chat doesn't exist, we 
                UUID chatUUIDString = UUID.fromString(jsonBody.getString("chat_uuid"));
                if (!knownChats.containsKey(chatUUIDString)) {
                    Chat newChat = new Chat();
                    knownChats.put(chatUUIDString, newChat);
                    res.setStatus(200);
                    //The documentation of this is lackluster at best, so yes, I used AI to figure this out
                    try {
                        OutputStream writer = res.getOutputStream();
                        res.setContentType("application/x-java-serialized-object");
    
                        ObjectOutputStream oos = new ObjectOutputStream(writer);
            
                        // 5. Write the object to the stream
                        oos.writeObject(newChat);
                        
                        // 6. Flush and close the streams
                        oos.flush();
                        oos.close();  
                    } catch (Exception e) {
                        System.err.println("There was an error.");
                        res.setStatus(500);
                        res.close();
                    }
                }
            }
        };


    }
}
