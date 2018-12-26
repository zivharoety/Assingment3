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
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }

    @Override
    public void procces() {
        if(!app.getActiveUsers().contains(protocol.getConnectionId())){
            Err toSend = new Err(app,(short) 3);
            app.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
            app.getUsers().get(app.getActiveUsers().contains(protocol.getConnectionId())).logout();
            app.getActiveUsers().remove(protocol.getConnectionId());
            ACK toSend = new ACK(app,(short) 3);
            app.getConnections().send(protocol.getConnectionId(),toSend);
        }

    }
}
