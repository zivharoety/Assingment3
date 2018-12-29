package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;

import java.sql.Connection;

public class Logout extends Message {

    public Logout(){
        super();
        op =3;

    }
    @Override
    public Message decode(byte b) {
        return this;
    }

    @Override
    public byte[] encode(Message msg) {
            byte[] toReturn = new byte[2];
            add2Bytes(toReturn,op);
        return toReturn;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app){
        User myUser = app.getActiveUsers().get(protocol.getConnectionId());
        if(myUser == null){
            Err toSend = new Err((short) 3);
            protocol.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
            myUser.logout();
            app.getActiveUsers().remove(protocol.getConnectionId());
            ACK toSend = new ACK((short) 3);
            toSend.setMyUser(app.getActiveUsers().get(protocol.getConnectionId()));
            if(protocol.getConnections().send(protocol.getConnectionId(),toSend)){
                protocol.terminate();
            }
        }


    }
}
