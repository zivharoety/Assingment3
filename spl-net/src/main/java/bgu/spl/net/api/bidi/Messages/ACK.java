package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.User;

import java.util.LinkedList;

public class ACK extends  Message {

    private short numOfusers;
    private LinkedList<String> UserList;
    private short numPosts;
    private short numFollowers;
    private short numFollowing;
    private short ackop;
    private User myUser;

    public ACK(BGSystem app){
        this.app = app;
        ;
    }
    public ACK(BGSystem app,short op){
        this.app = app;
        this.ackop = op;
    }

    @Override
    public Message decode(byte b) {
        return decodeOP(b);
    }

    @Override
    public byte[] encode(Message msg) {
        switch (ackop){
            case
        }
    }

    @Override
    public void process() {
        System.out.print("ACK " + op);
        switch(ackop) {
            case 2:
                myUser.activate(protocol.getConnectionId());
            case 3: {
                myUser.logout();
                app.getActiveUsers().remove(protocol.getConnectionId());
                protocol.terminate();
            }
            case 4:
                System.out.print(" " + numOfusers + " ");
                while (!getUserList().isEmpty()) {
                    System.out.print(getUserList().removeFirst());
                    if (!getUserList().isEmpty()) {
                        System.out.print(" ");
                    }
                }
            case 7:  System.out.print(" "+numOfusers+" ");
                while(!getUserList().isEmpty()){
                    System.out.print(getUserList().removeFirst());
                    if(!getUserList().isEmpty()){
                        System.out.print(" ");
                    }
                }


            case 8 :
                System.out.print(" "+numPosts+" "+numFollowers+" "+numFollowing);

        }
        System.out.println();


    }

    public short getNumOfusers() {
        return numOfusers;
    }

    public void setNumOfusers(short numOfusers) {
        this.numOfusers = numOfusers;
    }

    public LinkedList<String> getUserList() {
        return UserList;
    }

    public void setUserList(LinkedList<String> userList) {
        UserList = userList;
    }
    public void setStat(short numPosts,short numFollowers , short numFollowing){
        this.numPosts = numPosts;
        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }
}
