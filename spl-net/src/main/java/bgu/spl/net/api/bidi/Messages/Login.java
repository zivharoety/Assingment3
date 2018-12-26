package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import com.sun.tools.javac.util.Convert;
import sun.nio.cs.UTF_8;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Login extends Message {


    public Login(BGSystem app){
        this.app = app;
    }
    public void procces() {

        if(app.getUsers().contains(getFirstPart()) || !app.getUsers().get(getFirstPart()).getPassword().equals(getSecondPart()) || app.getUsers().get(getFirstPart()).isActive()){
            Err toSend = new Err(app,(short)2);
            app.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
            app.getUsers().get(getFirstPart()).activate(protocol.getConnectionId());
            ACK toSend = new ACK(app,(short)2);
            app.getActiveUsers().put(protocol.getConnectionId(),app.getUsers().get(getFirstPart()));
            app.getConnections().send(protocol.getConnectionId(),toSend);
        }



    }

    @Override
    public Message decode(byte b){
        return decode2Parts(b);
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }


}
