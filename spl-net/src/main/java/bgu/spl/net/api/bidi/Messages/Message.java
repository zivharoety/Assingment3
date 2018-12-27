package bgu.spl.net.api.bidi.Messages;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.api.bidi.ConnectionsImpl;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Arrays;

public abstract class Message {
   // protected BidiMessagingProtocolImpl protocol;
   // protected BGSystem app;
    protected String firstPart;
    protected String secondPart;
    protected int counter;
    protected int len;
    protected byte[] bytes;
    protected int userNameEnd;
    protected short op;
    protected int byteCounter;




    public abstract Message decode(byte b);
    public abstract byte[] encode(Message msg);
    public abstract void process(BidiMessagingProtocolImpl protocol, BGSystem app);


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
    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }
    public void add2Bytes (byte[] toReturn , short toConvert)
    {
        byte[] temp = shortToBytes(toConvert);
        toReturn[byteCounter] = temp[0];
        byteCounter++;
        toReturn[byteCounter] = temp[1];
        byteCounter++;
    }
/*
    public void setProtocol(BidiMessagingProtocolImpl ptorocol){
        protocol = protocol;
    }
*/
    public byte[] encode2Parts(){
        byte[] toReturn = new byte[4+getFirstPart().length()+getSecondPart().length()];
        byte[] temp = shortToBytes(op);
        toReturn[0] = temp[0];
        toReturn[1] = temp[1];
        byteCounter = 2;
        temp = getFirstPart().getBytes();
        encodeString(toReturn,temp);
        temp = getSecondPart().getBytes();
        encodeString(toReturn,temp);

        return toReturn;
    }

    public void encodeString(byte[] toReturn, byte[] temp){
        for(int i = 0 ; i < temp.length ; i++){
            toReturn[byteCounter+i] = temp[i];
        }
        byteCounter = byteCounter + temp.length;
        toReturn[byteCounter]  = '\0';
        byteCounter = byteCounter + 1;
    }

}
