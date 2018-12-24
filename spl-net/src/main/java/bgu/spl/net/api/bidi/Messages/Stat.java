package bgu.spl.net.api.bidi.Messages;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Stat implements Message {
    private byte[] bytes;
    private int len;
    private String userName;

    public Stat(){
        bytes= new byte[1<<10];
        len = 0;
    }
    @Override
    public Message decode(byte b) {
        pushByte(b);
        if(b == '\0') {
            userName = new String(bytes, StandardCharsets.UTF_8);
            return this;
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

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }
}
