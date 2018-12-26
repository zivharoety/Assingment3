package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import com.sun.tools.javac.util.Convert;
import sun.nio.cs.UTF_8;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PM extends Message {


    public PM(BGSystem app){
        this.app = app;

    }
    public void procces() {


    }

    @Override
    public Message decode(byte b) {

        return decode2Parts(b);
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }


}
