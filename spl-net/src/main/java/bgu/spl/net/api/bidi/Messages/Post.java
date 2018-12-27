package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.User;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Post extends Message {
   private BGSystem app;

    public Post(BGSystem app){
        this.app=app;
    }
    @Override
    public Message decode(byte b) {
        return decode1Part(b);
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }

    @Override
    public void procces() {
        User myUser = app.getActiveUsers().get(protocol.getConnectionId());
        if (myUser == null){
            Err error = new Err(app,(short)5);
            protocol.getConnections().send(protocol.getConnectionId(),error);
            return;
        }
            myUser.addPost();
        app.addMessage(this);
        String str = getFirstPart();
        Noti toNoti = new Noti(app, '1',this);
        while(str.indexOf('@') > -1){
            str = str.substring(str.indexOf('@'));
            String userName = str.substring(str.indexOf('@'),str.indexOf(' '));
            User toTag = app.getUsers().get(userName);
            if (app.getUsers().get(userName).isActive()) {
                app.getConnections().send(toTag.getCcurrentConectionId(), toNoti);
            }
            else{
                toTag.getPendingMessages().addLast(toNoti);
            }
            str.substring(1);
        }
        for(User f: myUser.getFollowers()){
            if(f.isActive()){
                app.getConnections().send(f.getCcurrentConectionId(), toNoti);

            }
            else{
                f.getPendingMessages().addLast(toNoti);
            }
        }

        ACK ack = new ACK(app,(short)5);
        protocol.getConnections().send(protocol.getConnectionId(),ack);

    }

}
