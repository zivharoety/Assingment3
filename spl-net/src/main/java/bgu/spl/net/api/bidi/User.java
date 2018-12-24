package bgu.spl.net.api.bidi;

import java.util.LinkedList;

public class User {
    int id;
    String userName;
    String password;
    LinkedList<User> following;
    LinkedList<User> followers;

   public User(int id, String userName,String password){
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.followers = new LinkedList<>();
        this.following = new LinkedList<>();
    }
}
