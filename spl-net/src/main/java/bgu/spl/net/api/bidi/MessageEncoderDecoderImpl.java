package bgu.spl.net.api.bidi;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.Messages.*;

import javax.management.Notification;
import java.util.Arrays;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<Message> {
    public BGSystem app ; // we need to get the app here !!!
    private byte[] bytes= new byte[1<<10];
    private int length = 0;
    short op = 0;
    Message currMessage;


    @Override
    public Message decodeNextByte(byte nextByte) {
        pushByte(nextByte);
        if(length == 2){
            toDecode();
            return null;
        }
        else if(length > 2){
            Message temp = currMessage.decode(nextByte);
            if(temp != null){
                bytes = new byte[1<<10];
                length = 0;
                return temp;
            }

        }


        return null;
    }

    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }

    private void pushByte(byte nextByte) {
        if (length >= bytes.length) {
            bytes = Arrays.copyOf(bytes, length * 2);
        }
        bytes[length++] = nextByte;
    }
    private Message toDecode() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
            short op = bytesToShort(bytes);
        switch (op) {
            case 1 : currMessage = new Register(app);
            case 2 : currMessage = new Login(app) ;
            case 3 : currMessage = new Logout(app);
                     return currMessage;
            case 4 : currMessage = new Follow(app);
            case 5 : currMessage = new Post(app);
            case 6 : currMessage = new PM(app);
            case 7 : currMessage = new UserList(app);
                    return currMessage;
            case 8 : currMessage = new Stat(app);
            case 9 : currMessage = new Noti(app);
            case 10: currMessage = new ACK(app);
            case 11: currMessage = new Err(app);



        }


        return null;



    }

    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
