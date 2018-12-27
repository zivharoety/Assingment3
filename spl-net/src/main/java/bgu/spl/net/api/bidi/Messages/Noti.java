package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Noti extends Message {


    private  char type;
    private Message content;

    public Noti(BGSystem app){
        this.app = app;
    }
    public Noti(BGSystem app, char status, Message content){
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
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }

    @Override
    public void procces() {


    }


    public char getType() {
        return type;
    }


    public Message getContent() {
        return content;
    }
}
