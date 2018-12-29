package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Arrays;

public class Noti extends Message {


    private  char type;
    private Timestamp stamp;


    public Noti(){
        super();
        this.op = 9;
    }
    public Noti(char status, String user, String content, Timestamp stamp){
        super();
        this.op = 9;
        this.type = status;
        firstPart= user;
        secondPart = content;
        this.stamp = stamp;
        if(secondPart.charAt(0) == '\0'){
            secondPart = content.substring(1);
        }
    }
    @Override
    public Message decode(byte b) {
        pushByte(b);
        if(len == 1){
            type = (char) b;
        }
        else{
            return decode2Parts(b);
        }
        return null;
    }

    @Override
    public byte[] encode(Message msg) {
        byte[] toReturn = new byte[5+getFirstPart().length()+getSecondPart().length()];
        add2Bytes(toReturn,op);
        toReturn[2] = (byte) type;
        byteCounter++;
        byte[] temp = getFirstPart().getBytes();
        encodeString(toReturn,temp);
        temp = getSecondPart().getBytes();
        encodeString(toReturn,temp);
        return toReturn;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app) {


    }


    public char getType() {
        return type;
    }


    public String getContent() {
        return secondPart;
    }

    public Timestamp getStamp() {
        return stamp;
    }
}
