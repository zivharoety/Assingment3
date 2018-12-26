package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;

public class UserList extends Message {

    public UserList(BGSystem app){
        this.app = app;
    }
    @Override
    public Message decode(byte b) {
        return null;
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }

    @Override
    public void procces() {

    }
}
