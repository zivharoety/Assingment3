package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.User;
import com.sun.tools.javac.util.Convert;
import sun.nio.cs.UTF_8;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Login extends Message {


    public Login(BGSystem app){
        this.app = app;
    }
    public void process() {
        User myUser = app.getUsers().get(getFirstPart());
        if(myUser == null || !myUser.getPassword().equals(getSecondPart()) || myUser.isActive()){
            Err toSend = new Err(app,(short)2);
            app.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
           // myUser.activate(protocol.getConnectionId()); move to ack
            ACK toSend = new ACK(app,(short)2);
            toSend.setMyUser(myUser);
            app.getActiveUsers().put(protocol.getConnectionId(),myUser);
            app.getConnections().send(protocol.getConnectionId(),toSend);
            while(!myUser.getPendingMessages().isEmpty()){
                myUser.getPendingMessages().pop().process();
            }
        }



    }

    @Override
    public Message decode(byte b){
        return decode2Parts(b);
    }

    @Override
    public byte[] encode(Message msg) {
        return encode2Parts();
    }


}
