package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;
import java.util.Comparator;
import java.util.LinkedList;

public class Login extends Message {

    public Login(){
        super();
        op = 2;
    }
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app){
        User myUser = app.getUsers().get(getFirstPart());
        if(myUser == null || !myUser.getPassword().equals(getSecondPart()) || myUser.isActive() || app.getActiveUsers().containsKey(protocol.getConnectionId())){
            Err toSend = new Err((short)2);
            protocol.getConnections().send(protocol.getConnectionId(),toSend);
        }
        else{
            ACK toSend = new ACK((short)2);
            toSend.setMyUser(myUser);
            app.getActiveUsers().put(protocol.getConnectionId(),myUser);
            protocol.getConnections().send(protocol.getConnectionId(),toSend);
            myUser.activate(protocol.getConnectionId());
            Comparator<Noti> comp = new Comparator<Noti>() {
                @Override
                public int compare(Noti one, Noti two) {
                    if (one.getStamp().before(two.getStamp()))
                        return -1;
                    if (one.getStamp().after(two.getStamp()))
                        return 1;
                    else
                        return 0;                }
            };
            LinkedList<Noti> temp = new LinkedList<>();
            while (!myUser.getPendingMessages().isEmpty()){
                temp.addLast(myUser.getPendingMessages().poll());
            }
            temp.sort(comp);
            while(!temp.isEmpty()){
                protocol.getConnections().send(protocol.getConnectionId(),temp.pop());
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

    public String toString() {
        return "LOGIN " + getFirstPart() + " " + getSecondPart();
    }
}
