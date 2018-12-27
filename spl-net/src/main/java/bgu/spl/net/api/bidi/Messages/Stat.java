package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.User;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Stat extends Message {


    public Stat(BGSystem app){
        this.app = app;
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
        User toGetStat = app.getUsers().get(getFirstPart());
        if (myUser == null || toGetStat == null){
            Err error = new Err(app,(short)8);
            protocol.getConnections().send(protocol.getConnectionId(),error);
            return;
        }
        else{
            ACK ack = new ACK(app,(short)8);
            ack.setStat((short)toGetStat.getNumOfpost(),(short)toGetStat.getFollowers().size(),(short)toGetStat.getFollowing().size());
            protocol.getConnections().send(protocol.getConnectionId(),ack);

        }
    }

}
