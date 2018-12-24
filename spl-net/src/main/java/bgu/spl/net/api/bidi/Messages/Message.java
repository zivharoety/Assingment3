package bgu.spl.net.api.bidi.Messages;

public interface Message {


    public Message decode(byte b);

    public Byte[] encode(Message msg);

    public void procces();

}
