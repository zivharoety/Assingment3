package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.ConnectionsImpl;

import java.sql.Connection;

public class Err extends Message {
    private short opErr;
    public Err(){
        super();
        this.op = 11;
    }

    public Err(short op){
        super();
        this.op = 11;
        this.opErr = op;
    }
    @Override
    public Message decode(byte b) {
        return decodeOP(b);
    }

    @Override
    public byte[] encode(Message msg) {
        byte[] toReturn = new byte[4];
        add2Bytes(toReturn , op);
        add2Bytes(toReturn ,opErr);
        return toReturn;
    }

    @Override
    public void process(BidiMessagingProtocolImpl protocol, BGSystem app) {
        System.out.println("Error "+opErr);
    }

    public short getOpErr() {
        return opErr;
    }
}
