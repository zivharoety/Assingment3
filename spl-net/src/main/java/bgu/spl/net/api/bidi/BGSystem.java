package bgu.spl.net.api.bidi;

import bgu.spl.net.api.bidi.Messages.Message;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class BGSystem {
    private int connectionCounter;
    private ConcurrentHashMap<String,User> users;
    private ConcurrentHashMap<Integer,User> activeUsers;
    private LinkedList<Message> data ;

    public BGSystem(){
        users = new ConcurrentHashMap<>();
        connectionCounter = 0;
        activeUsers = new ConcurrentHashMap<>();
        data = new LinkedList<>() ;
    }

    public ConcurrentHashMap<String, User> getUsers() {
        return users;
    }

    public int getConnectionCounter() {
        return connectionCounter;
    }

    public ConcurrentHashMap<Integer, User> getActiveUsers() {
        return activeUsers;
    }

    public void addMessage(Message msg){
        synchronized (data){
            data.add(msg);
        }
    }
}
