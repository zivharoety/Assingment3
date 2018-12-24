package bgu.spl.net.api.bidi.Messages;

import com.sun.tools.javac.util.Convert;
import sun.nio.cs.UTF_8;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PM implements Message {
    private String userName;
    private String content;
    private int counter;
    private byte[] bytes;
    private int len;
    private int userNameEnd;

    public PM(){

        this.userName = null;
        this.content = null;
        this.counter = 0;
        bytes= new byte[1<<10];
        len = 0;
    }
    public void procces() {


    }

    @Override
    public Message decode(byte b) {
        if(b == '\0')
            counter++;
        pushByte(b);
        if(counter == 1){
            userName = new String (bytes,0,len,StandardCharsets.UTF_8);
            userNameEnd = len;
        }
        else if(counter == 2){
            content= new String(bytes,userNameEnd,len,StandardCharsets.UTF_8);
            return this;
        }
        return null;
    }

    @Override
    public Byte[] encode(Message msg) {
        return new Byte[0];
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
}
