package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;

public class Register extends Message {

    public Register(){
        super();
        op = 1;

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
        if(app.getUsers().containsKey(getFirstPart())){
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
    public String toString() {
        return "REGISTER " + getFirstPart() + " " + getSecondPart();
    }


    }

