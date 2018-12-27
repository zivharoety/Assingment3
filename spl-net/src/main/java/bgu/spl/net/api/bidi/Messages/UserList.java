package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;

import java.sql.Connection;
import java.util.Comparator;
import java.util.LinkedList;

public class UserList extends Message {
    private LinkedList<User> users;
    public UserList(){
        this.users = new LinkedList<>();
    }
    @Override
    public Message decode(byte b) {
        return null;
    }

    @Override
    public byte[] encode(Message msg) {
      byte[] toReturn = new byte[2];
      add2Bytes(toReturn , op);
      return toReturn;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app){
        User myUser = app.getActiveUsers().get(protocol.getConnectionId());
        if (myUser == null){
            Err error = new Err((short)7);
            protocol.getConnections().send(protocol.getConnectionId(),error);
            return;
        }
        for(User u : app.getUsers().values()){
            users.add(u);
        }
        Comparator<User> comp = new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                if (user.getId() > t1.getId()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        users.sort(comp);
        ACK ack = new ACK((short)7);
        LinkedList<String> toReturn = new LinkedList<>();
        for(int i=0 ; i < users.size();i++){
            toReturn.addLast(users.get(i).getUserName());
        }
        ack.setUserList(toReturn);
        protocol.getConnections().send(protocol.getConnectionId(),ack);



    }
}
