package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Arrays;
import java.util.LinkedList;

public class Follow extends Message {
    private int numOfUsers;
    private int usercounter;
    private char status;
    private LinkedList<String> users;
    private int pos;


    public Follow() {
        super();
        op = 4;
        pos = 3;
        users = new LinkedList<>();
    }

    @Override
    public Message decode(byte b) {

        pushByte(b);
        if (len == 1) {
            status = (char) bytes[0];

        } else if (len == 3) {
            byte[] num = {bytes[1], bytes[2]};
            numOfUsers = new BigInteger(num).intValue();
        } else if (b == '\0' & usercounter < numOfUsers - 1 && len > 3) {
            users.add(new String(bytes, pos, len, StandardCharsets.UTF_8));
            pos = len;
            usercounter++;
            return null;
        } else if (b == '\0' & usercounter == numOfUsers - 1 && len > 3) {
            String name = new String(bytes, pos, len, StandardCharsets.UTF_8);
            name = name.substring(0, name.indexOf('\0'));
            users.add(name);
            return this;
        }
        return null;
    }

    @Override
    public byte[] encode(Message msg) {
        String userList = "";
        while (!users.isEmpty()) {
            userList = userList + users.removeFirst() + '\0';
        }
        byte[] toReturn = new byte[4 + userList.length()];
        add2Bytes(toReturn, op);
        toReturn[2] = (byte) status;
        byteCounter++;
        add2Bytes(toReturn, (short) numOfUsers);
        byte[] temp = userList.getBytes();
        encodeStringNoZero(toReturn, temp);
        return toReturn;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app) {
        LinkedList<String> userList = new LinkedList<>();

        Err Error = new Err((short) 4);
        ACK ack = new ACK((short) 4);
        if (app.getActiveUsers().get(protocol.getConnectionId()) == null) {
            protocol.getConnections().send(protocol.getConnectionId(), Error);
            return;
        }
        if (status == '\0') {
            for (String s : users) {
                if (!(s.indexOf('\0') == -1))
                    s = s.substring(0, s.indexOf('\0'));
                if (!app.getActiveUsers().get(protocol.getConnectionId()).isFollowing(s)) {
                    User u = app.getUsers().get(s);
                    app.getActiveUsers().get(protocol.getConnectionId()).follow(u);
                    userList.add(s);
                }
            }
        } else {
            for (String s : users) {
                if (!(s.indexOf('\0') == -1))
                    s = s.substring(0, s.indexOf('\0'));
                if (app.getActiveUsers().get(protocol.getConnectionId()).isFollowing(s)) {
                    app.getActiveUsers().get(protocol.getConnectionId()).unfollow(app.getUsers().get(s));
                    userList.add(s);
                }
            }

        }
        if (userList.size() == 0) {
            protocol.getConnections().send(protocol.getConnectionId(), Error);
        } else {
            ack.setNumOfusers((short) userList.size());
            ack.setUserList(userList);
            ack.setStatus((short) status);
            protocol.getConnections().send(protocol.getConnectionId(), ack);
            numOfUsers = userList.size();
        }


    }


    public int getNumOfUsers() {
        return numOfUsers;
    }

    public char getStatus() {
        return status;
    }

}
