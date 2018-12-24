package bgu.spl.net.api.bidi.Messages;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Noti implements Message {
    private int counter;
    private byte[] bytes;
    private int len;
    private  char type;
    private String content;
    private String postingUser;

    public Noti(){
        this.counter = 0;
        bytes= new byte[1<<10];
        len = 0;
    }
    @Override
    public Message decode(byte b) {
        pushByte(b);
        if(len == 1){
            type = (char) b;
        }
       /* else if(len == 3){
            byte[] num = {bytes[1],bytes[2]};
            numOfUsers = new BigInteger(num).intValue();
        }
        else if(b == '\0'){
            userNameList = new String(bytes,3,len,StandardCharsets.UTF_8);
            return this;
        }*/
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

    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

}
