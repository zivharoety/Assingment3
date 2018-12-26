package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.User;
import com.sun.tools.javac.util.Convert;
import sun.nio.cs.UTF_8;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Register extends Message {

    public Register(BGSystem app){
        this.app = app;
    }
    @Override
    public Message decode(byte b) {
       return decode2Parts(b);
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }


    public void procces() {

        if(app.getUsers().contains(getFirstPart())){
            Err toSend = new Err(app,(short)1);

            app.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
            User toAdd = new User(app.getUsers().size()+1,getFirstPart(),getSecondPart());
            app.getUsers().put(getFirstPart(),toAdd);
            ACK toSend = new ACK(app, (short) 1);
            app.getConnections().send(protocol.getConnectionId(),toSend);
        }

        }

    }

