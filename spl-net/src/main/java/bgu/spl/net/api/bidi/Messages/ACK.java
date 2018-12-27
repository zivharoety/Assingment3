package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;

import java.util.LinkedList;

public class ACK extends  Message {

    private short numOfusers;
    private LinkedList<String> UserList;
    private short numPosts;
    private short numFollowers;
    private short numFollowing;
    public ACK(BGSystem app){
        this.app = app;
        ;
    }
    public ACK(BGSystem app,short op){
        this.app = app;
        this.op = op;
    }

    @Override
    public Message decode(byte b) {
        return decodeOP(b);
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }

    @Override
    public void procces() {

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
}
