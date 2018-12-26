package bgu.spl.net.api.bidi;

import bgu.spl.net.api.bidi.Messages.Message;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol {

        private int connectionId;
        private ConnectionsImpl connection;


    @Override
    public void start(int connectionId, Connections connections) {
        this.connectionId = connectionId;
        this.connection = connection;
    }

    @Override
    public void process(Object message) {
        ((Message) message).procces();

    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public ConnectionsImpl getConnections(){
        return connection;
    }
}
