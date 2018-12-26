package bgu.spl.net.api.bidi;

import java.util.LinkedList;

public class User {
    private int id;
    private String userName;
    private String password;
    private LinkedList<User> following;
    private LinkedList<User> followers;
    private boolean active;
    private int currentConectionId;
    private int numOfpost;

   public User(int id, String userName,String password){
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.followers = new LinkedList<>();
        this.following = new LinkedList<>();
        this.active = false;
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
        currentConectionId = id ;
    }

    public int getCcurrentConectionId() {
        return currentConectionId;
    }

    public void logout(){
       active = false;
       currentConectionId = -1;
    }

    public boolean isFollowing(String name){
       for(User u : following) {
           if (u.getUserName() == name) {
               return true;
           }
       }
       return false;
    }

    public void follow(User user){
        following.add(user);
        user.addFollower(this);

    }
    public void unfollow(User user){
       following.remove(user);
       user.removeFollower(this);
    }
    public void addFollower(User user){
       followers.add(user);
    }

    public void removeFollower(User user){
        followers.remove(user);
    }


    public int getNumOfpost() {
        return numOfpost;
    }
    public void addPost(){
       numOfpost++;
    }
}
