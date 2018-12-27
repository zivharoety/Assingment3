package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class Follow extends Message {
    private int numOfUsers;
    private int usercounter;
    private  char status;
    private LinkedList<String> users;
    private int pos;


    public Follow(BGSystem app){
        this.app = app;
        pos = 3;
        users = new LinkedList<>();
    }
    @Override
    public Message decode(byte b) {

        pushByte(b);
        if(len == 1){
            status = (char) bytes[0];

        }
        else if(len == 3){
            byte[] num = {bytes[1],bytes[2]};
             numOfUsers = new BigInteger(num).intValue();
        }
        else if(b == '\0' & usercounter<numOfUsers){
            users.add(new String(bytes,pos,len,StandardCharsets.UTF_8));
            return null;
        }
        else if(b == '\0' & usercounter==numOfUsers) {
            users.add(new String(bytes, pos, len, StandardCharsets.UTF_8));
            return this;
        }
        return null;
    }

    @Override
    public byte[] encode(Message msg) {
       String userList = "";
        while(!users.isEmpty()){
           userList = userList + users.removeFirst()+'\0';
       }
        byte[] toReturn = new byte[5+userList.length()];
        addOpbyte(toReturn);
        toReturn[2] = (byte) status;
        byte[] temp = shortToBytes((short) numOfUsers);
        toReturn[3] = temp[0];
        toReturn[4] = temp[1];
        temp  = userList.getBytes();
        for(int i = 0 ; i < temp.length ; i ++){
            toReturn[i+5] = temp[i];
        }

        return toReturn;

    }

    @Override
    public void process() {
        LinkedList<String> userList = new LinkedList<>();

        Err Error = new Err(app,(short)4);
        ACK ack = new ACK(app,(short)4);
        if(app.getActiveUsers().get(protocol.getConnectionId()) == null){
            app.getConnections().send(protocol.getConnectionId(),Error);
            return;
        }
        if(status == '0'){
         for(String s : users){
             if(!app.getActiveUsers().get(protocol.getConnectionId()).isFollowing(s)){
                 app.getActiveUsers().get(protocol.getConnectionId()).follow(app.getUsers().get(s));
                 userList.add(s);
             }
         }
        }
        else{
            for(String s : users){
                if(app.getActiveUsers().get(protocol.getConnectionId()).isFollowing(s)){
                    app.getActiveUsers().get(protocol.getConnectionId()).unfollow(app.getUsers().get(s));
                    userList.add(s);
                }
            }

        }
        if(userList.size() == 0){
            app.getConnections().send(protocol.getConnectionId(),Error);
        }
        else{
            ack.setNumOfusers((short) userList.size());
            ack.setUserList(userList);
            app.getConnections().send(protocol.getConnectionId(),ack);
            numOfUsers = userList.size();
        }



    }


    public int getNumOfUsers() {
        return numOfUsers;
    }

    public char getStatus() {
        return status;
    }

}
