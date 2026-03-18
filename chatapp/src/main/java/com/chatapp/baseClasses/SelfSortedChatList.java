package com.chatapp.baseClasses;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.UUID;

public class SelfSortedChatList<K, V extends Chat> extends LinkedHashMap<K, V> {
    public void update(Message message, K key){
        V value = this.get(key);
        value.sendMessage(message);
        this.remove(key);
        this.put(key, value);
    }
    public Iterator<V> iterator(){
        LinkedList<V> list = (LinkedList<V>) this.values();
        return list.descendingIterator();
    }
    
    public V[] toArray(){
        LinkedList<V> list = (LinkedList<V>) this.values();
        return (V[]) list.toArray();
    }

}
