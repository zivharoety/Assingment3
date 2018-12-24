package bgu.spl.net.api.bidi.Messages;

public class Logout implements Message {

    public Logout(){
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
