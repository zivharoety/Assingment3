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
    public byte[] encode(Message msg) {
        byte[] toReturn = new byte[3+getFirstPart().length()];
        byte[] temp = getFirstPart().getBytes();
        addOpbyte(toReturn);
        for(int i=0;i<temp.length;i++){
            toReturn[i+byteCounter] = temp[i];
        }
        toReturn[toReturn.length-1] = '\0';
        return  toReturn;
    }

    @Override
    public void process() {
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
