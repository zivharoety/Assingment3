package bgu.spl.net.api.bidi;

import bgu.spl.net.api.bidi.Messages.Message;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Message> {

    private int connectionId;
    private ConnectionsImpl connection;
    private boolean terminate;


    @Override
    public void start(int connectionId, Connections connections) {
        this.connectionId = connectionId;
        this.connection = connection;
        terminate = false;
    }

    @Override
    public void process(Message message) {
        message.process();

    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public ConnectionsImpl getConnections() {
        return connection;
    }


    public void terminate() {
        terminate = true;
    }
}