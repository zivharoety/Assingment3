package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;

public class Logout extends Message {

    public Logout(BGSystem app){
        this.app = app;
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
    public void process() {
        if(!app.getActiveUsers().contains(protocol.getConnectionId())){
            Err toSend = new Err(app,(short) 3);
            app.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
            ACK toSend = new ACK(app,(short) 3);
            toSend.setMyUser(app.getActiveUsers().get(protocol.getConnectionId()));
            app.getConnections().send(protocol.getConnectionId(),toSend);
        }

    }
}
