package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;


public class Login extends Message {


    public Login(){
    }
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app){
        User myUser = app.getUsers().get(getFirstPart());
        if(myUser == null || !myUser.getPassword().equals(getSecondPart()) || myUser.isActive()){
            Err toSend = new Err((short)2);
            protocol.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
           // myUser.activate(protocol.getConnectionId()); move to ack
            ACK toSend = new ACK((short)2);
            toSend.setMyUser(myUser);
            app.getActiveUsers().put(protocol.getConnectionId(),myUser);
            protocol.getConnections().send(protocol.getConnectionId(),toSend);
            while(!myUser.getPendingMessages().isEmpty()){
                myUser.getPendingMessages().pop().process(protocol,app);
            }
        }



    }

    @Override
    public Message decode(byte b){
        return decode2Parts(b);
    }

    @Override
    public byte[] encode(Message msg) {
        return encode2Parts();
    }


}
