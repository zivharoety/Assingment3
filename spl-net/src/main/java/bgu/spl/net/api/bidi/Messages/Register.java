package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;
import com.sun.tools.javac.util.Convert;
import sun.nio.cs.UTF_8;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Arrays;

public class Register extends Message {

    public Register(){
    }
    @Override
    public Message decode(byte b) {
       return decode2Parts(b);
    }

    @Override
    public byte[] encode(Message msg) {


        return encode2Parts();
    }


    public void process(BidiMessagingProtocolImpl protocol, BGSystem app){

        if(app.getUsers().contains(getFirstPart())){
            Err toSend = new Err((short)1);

            protocol.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
            User toAdd = new User(app.getUsers().size()+1,getFirstPart(),getSecondPart());
            app.getUsers().put(getFirstPart(),toAdd);
            ACK toSend = new ACK((short) 1);
            protocol.getConnections().send(protocol.getConnectionId(),toSend);
        }

        }

    }

