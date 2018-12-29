package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;

import java.sql.Connection;
import java.util.LinkedList;

public class ACK extends  Message {

    private short numOfusers;
    private LinkedList<String> UserList;
    private short numPosts;
    private short numFollowers;
    private short numFollowing;
    private short ackop;
    private User myUser;
    private short status;

    public ACK(){
        super();
        this.op = 10;
    }

    public ACK(short op){
        super();
        this.ackop = op;
        this.op = 10;
    }

    @Override
    public Message decode(byte b) {
        return decodeOP(b);
    }

    @Override
    public byte[] encode(Message msg) {
        switch (ackop){
            case 4:
                String userList4 = "";
                String temp = "";
                while(!getUserList().isEmpty()){
                    userList4 = userList4 + getUserList().removeFirst();
                    if(!userList4.isEmpty()){
                        userList4 = userList4 + '\0';
                    }
                }

                byte[] temp4 = userList4.getBytes();
                byte[] toReturn4 = new byte[8+temp4.length];
                add2Bytes(toReturn4 , op);
                add2Bytes(toReturn4 , (short) 4);
                add2Bytes(toReturn4 , numOfusers);
         //       toReturn4[byteCounter] = '\0';
           //     byteCounter++;
                encodeString(toReturn4 , temp4);
                return toReturn4;
            case 7:
                String userList7 = "";
                while(!getUserList().isEmpty()){
                    userList7 = userList7 + getUserList().removeFirst()+'\0';
                }
                byte[] temp7 = userList7.getBytes();
                byte[] toReturn7 = new byte[7+temp7.length];
                add2Bytes(toReturn7 , op);
                add2Bytes(toReturn7 , (short) 7);
                add2Bytes(toReturn7 , numOfusers);
                encodeString(toReturn7 , temp7);
                return toReturn7;
            case 8:
                byte[] toReturn8 = new byte[10];
                add2Bytes(toReturn8 , op);
                add2Bytes(toReturn8 , (short) 8);
                add2Bytes(toReturn8 , numPosts);
                add2Bytes(toReturn8 , numFollowers);
                add2Bytes(toReturn8 , numFollowing);
                return toReturn8;

                default:
                    byte[] toReturn = new byte[4];
                    add2Bytes(toReturn , op);
                    add2Bytes(toReturn , ackop);
                    return toReturn;

        }

    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app){
  /*      System.out.print("ACK " + op);
        switch(ackop) {
      redundant      case 2:
                myUser.activate(protocol.getConnectionId());*
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
        */


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

    public void setStatus(short status) {
        this.status = status;
    }
}
