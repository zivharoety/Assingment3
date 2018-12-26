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
        if (myUser != null)
            myUser.addPost();
        app.addMessage(this);
        String str = getFirstPart();
        while (str != "") {
            str = str.substring(str.indexOf('@'));
            String user =  str.substring(str.indexOf(' '));
            User toTag = app.getUsers().get(user);
            if(app.getUsers().get(user).isActive()){
                Noti toNoti = new Noti(app,0);
                app.getConnections().send(toTag.getCcurrentConectionId(),toNoti);
            }
            /// to contiue

        }

    }

}
