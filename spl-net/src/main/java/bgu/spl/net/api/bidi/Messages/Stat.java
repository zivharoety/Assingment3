package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Stat extends Message {


    public Stat(BGSystem app){
        this.app = app;
    }
    @Override
    public Message decode(byte b) {
      return decode1Part(b);
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }

    @Override
    public void procces() {
    }

}
