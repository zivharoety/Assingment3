package bgu.spl.net.api.bidi;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class BGSystem {
    public ConcurrentHashMap<String,User> users;

    private BGSystem(){
        users = new ConcurrentHashMap<>();
    }
    private static class BGSYstemHolder{
        private static BGSystem instance = new BGSystem();
    }
    public static BGSystem getInstance(){
        return BGSYstemHolder.instance;
    }
}
