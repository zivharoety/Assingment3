package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.ConnectionsImpl;

public class Err extends Message {

    public Err(BGSystem app){
        this.app = app;
    }

    public Err(BGSystem app,short op){
        this.app = app;
        this.op = op;
    }
    @Override
    public Message decode(byte b) {
        return decodeOP(b);
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }

    @Override
    public void procces() {

    }
}
