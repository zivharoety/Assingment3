package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

import java.sql.Connection;

public class Logout extends Message {

    public Logout(){

    }
    @Override
    public Message decode(byte b) {
        return null;
    }

    @Override
    public byte[] encode(Message msg) {

        return shortToBytes(op);
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app){
        if(!app.getActiveUsers().contains(protocol.getConnectionId())){
            Err toSend = new Err((short) 3);
            protocol.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
            ACK toSend = new ACK((short) 3);
            toSend.setMyUser(app.getActiveUsers().get(protocol.getConnectionId()));
            protocol.getConnections().send(protocol.getConnectionId(),toSend);
        }

    }
}
