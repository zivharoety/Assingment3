package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.User;
import com.sun.tools.javac.util.Convert;
import sun.nio.cs.UTF_8;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PM extends Message {


    public PM(BGSystem app){
        this.app = app;

    }
    public void procces() {
        User myUser = app.getActiveUsers().get(protocol.getConnectionId());
        User toSend = app.getUsers().get(getFirstPart());
        if (myUser == null || toSend == null){
            Err error = new Err(app,(short)6);
            protocol.getConnections().send(protocol.getConnectionId(),error);
            return;
        }
        app.addMessage(this);
        Noti toNoti = new Noti(app,'0',this);
        if(toSend.isActive()){
            protocol.getConnections().send(toSend.getCcurrentConectionId(),toNoti);
        }
        else{
            toSend.getPendingMessages().addLast(toNoti);
        }
        ACK ack = new ACK(app,(short)6);
        protocol.getConnections().send(myUser.getCcurrentConectionId(),ack);

    }

    @Override
    public Message decode(byte b) {

        return decode2Parts(b);
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }


}
