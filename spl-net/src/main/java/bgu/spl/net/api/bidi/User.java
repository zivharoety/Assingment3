package bgu.spl.net.api.bidi;

import bgu.spl.net.api.bidi.Messages.Message;
import bgu.spl.net.api.bidi.Messages.Noti;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class User {
    private int id;
    private String userName;
    private String password;
    private LinkedList<User> following;
    private LinkedList<User> followers;
    private boolean active;
    private int currentConnectionId;
    private int numOfpost;
    private LinkedList<Noti> pendingMessages;

   public User(int id, String userName,String password){
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.followers = new LinkedList<>();
        this.following = new LinkedList<>();
        this.active = false;
        this.pendingMessages = new LinkedList<>();
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public LinkedList<User> getFollowing() {
        return following;
    }

    public LinkedList<User> getFollowers() {
        return followers;
    }

    public boolean isActive() {
        return active;
    }

    public void activate(int id) {
        this.active = true;
        currentConnectionId = id ;
    }

    public int getCcurrentConectionId() {
        return currentConnectionId;
    }

    public void logout(){
       active = false;
       currentConnectionId = -1;
    }

    public boolean isFollowing(String name){
       for(User u : following) {
           if (u.getUserName().equals(name)) {
               return true;
           }
       }
       return false;
    }

    public void follow(User user){
       synchronized (following) {
           following.add(user);
       }
        user.addFollower(this);

    }
    public void unfollow(User user){
       synchronized (following) {
           following.remove(user);
       }
       user.removeFollower(this);
    }
    public void addFollower(User user){
       synchronized (followers) {
           followers.add(user);
       }
    }

    public void removeFollower(User user){
       synchronized (followers) {
           followers.remove(user);
       }
    }


    public int getNumOfpost() {
        return numOfpost;
    }
    public void addPost(){
       numOfpost++;
    }

    public LinkedList<Noti> getPendingMessages() {
        return pendingMessages;
    }

    public int getId() {
        return id;
    }
}
