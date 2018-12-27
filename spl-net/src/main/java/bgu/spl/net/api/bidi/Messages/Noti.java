package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Noti extends Message {


    private  char type;
    private String content;

    public Noti(BGSystem app){
        this.app = app;
    }
    public Noti(BGSystem app, char status, String content){
        this.app = app;
        this.type = status;
        this.content = content;
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
        addOpbyte(toReturn);
        toReturn[2] = (byte) type;
        byteCounter++;
        byte[] temp = getFirstPart().getBytes();
        encodeString(toReturn,temp);
        temp = getSecondPart().getBytes();
        encodeString(toReturn,temp);
        return toReturn;
    }

    @Override
    public void process() {


    }


    public char getType() {
        return type;
    }


    public String getContent() {
        return content;
    }
}
