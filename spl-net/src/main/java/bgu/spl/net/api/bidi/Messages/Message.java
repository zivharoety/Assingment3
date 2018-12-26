package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.ConnectionsImpl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class Message {
    protected BidiMessagingProtocolImpl protocol;
    protected BGSystem app;
    protected String firstPart;
    protected String secondPart;
    protected int counter;
    protected int len;
    protected byte[] bytes;
    protected int userNameEnd;
    protected short op;




    public abstract Message decode(byte b);
    public abstract Byte[] encode(Message msg);
    public abstract void procces();


    public Message decode2Parts(byte b){
        if(b == '\0')
            counter++;
        pushByte(b);
        if(counter == 1){
            firstPart = new String (bytes,0,len, StandardCharsets.UTF_8);
            userNameEnd = len;
        }
        else if(counter == 2){
            secondPart = new String(bytes,userNameEnd,len,StandardCharsets.UTF_8);
            return this;
        }
        return null;
    }
    public Message decode1Part(byte b) {
        pushByte(b);
        if(b == '\0') {
            firstPart = new String(bytes, StandardCharsets.UTF_8);
            return this;
        }
        return null;
    }

    public Message decodeOP(byte b){
        pushByte(b);
        if(len==2){
            byte[] t = {bytes[0],bytes[1]};
            op = bytesToShort(t);
            return this;
        }
        return null;
    }

    protected void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }


    public String getFirstPart() {
        return firstPart;
    }

    public String getSecondPart() {
        return secondPart;
    }

    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

    public void setProtocol(BidiMessagingProtocolImpl ptorocol){
        protocol = protocol;
    }
}
