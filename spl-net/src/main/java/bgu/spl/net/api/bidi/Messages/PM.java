package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;

import java.sql.Timestamp;


public class PM extends Message {


    public PM(){
        super();
        op = 6;

    }
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app) {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());

        User myUser = app.getActiveUsers().get(protocol.getConnectionId());
        User toSend = app.getUsers().get(getFirstPart());
        if (myUser == null || toSend == null){
            Err error = new Err((short)6);
            protocol.getConnections().send(protocol.getConnectionId(),error);
            return;
        }
        app.addMessage(this);
        String content = getSecondPart();
        if (getFirstPart().indexOf('\0')!= -1){
            content = content.substring(0,content.indexOf('\0'));
        }
        Noti toNoti = new Noti('0',getFirstPart(),getSecondPart(),stamp);
        if(toSend.isActive()){
            protocol.getConnections().send(toSend.getCcurrentConectionId(),toNoti);
        }
        else{
            toSend.getPendingMessages().addLast(toNoti);
        }
        ACK ack = new ACK((short)6);
        protocol.getConnections().send(myUser.getCcurrentConectionId(),ack);

    }

    @Override
    public Message decode(byte b) {

        return decode2Parts(b);
    }

    @Override
    public byte[] encode(Message msg) {
        return encode2Parts();
    }


}
