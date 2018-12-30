package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Arrays;

public class Post extends Message {


    public Post(){
        super();
        op = 5;

    }
    @Override
    public Message decode(byte b) {
        return decode1Part(b);
    }

    @Override
    public byte[] encode(Message msg) {
        byte[] temp = getFirstPart().getBytes();
        byte[] toReturn = new byte[temp.length+3];
        add2Bytes(toReturn , op);
        encodeString(toReturn,temp);
        return toReturn;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app){
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        User myUser = app.getActiveUsers().get(protocol.getConnectionId());
        if (myUser == null){
            Err error = new Err((short)5);
            protocol.getConnections().send(protocol.getConnectionId(),error);
            return;
        }
            myUser.addPost();
        app.addMessage(this);
        String str = getFirstPart().substring(0,getFirstPart().indexOf('\0'));
        String temp = str;
        while(str.indexOf('@') > -1) {
            Noti toNoti = new Noti('1', myUser.getUserName(), temp, stamp);
            str = str.substring(str.indexOf('@') + 1);
            String userName = str;
            if (userName.indexOf(' ') != -1) {
                userName = str.substring(0, str.indexOf(' '));
            }
            if (userName.indexOf('@') != -1) {
                userName = str.substring(0, str.indexOf('@'));
            }
            User toTag = app.getUsers().get(userName);
            if (!toTag.isFollowing(myUser.getUserName())) {

                if (app.getUsers().get(userName).isActive()) {
                    protocol.getConnections().send(toTag.getCcurrentConectionId(), toNoti);
                } else {
                    toTag.getPendingMessages().addLast(toNoti);
                }

            }
        }
        for(User f: myUser.getFollowers()){
            Noti toNoti = new Noti( '1',myUser.getUserName(),temp,stamp);
            if(f.isActive()){
                protocol.getConnections().send(f.getCcurrentConectionId(), toNoti);

            }
            else{
                f.getPendingMessages().addLast(toNoti);
            }
        }

        ACK ack = new ACK((short)5);
        protocol.getConnections().send(protocol.getConnectionId(),ack);

    }

}
