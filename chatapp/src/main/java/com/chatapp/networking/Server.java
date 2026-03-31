package com.chatapp.networking;

import java.util.HashMap;
import java.util.UUID;

import com.chatapp.baseClasses.Chat;

public class Server {
    HashMap<UUID,Chat> knownChats;
    HashMap<UUID,String> ipMappingTable;
}
