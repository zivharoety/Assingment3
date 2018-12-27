package bgu.spl.net.api.bidi;

import bgu.spl.net.api.bidi.Messages.Message;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Message> {

    private int connectionId;
    private Connections connection;
    private boolean terminate;
    private static BGSystem app = new BGSystem();


    @Override
    public void start(int connectionId, Connections connections) {
        this.connectionId = connectionId;
        this.connection = connections;
        terminate = false;
    }

    @Override
    public void process(Message message) {
        message.process(this,app);

    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public Connections getConnections() {
        return connection;
    }


    public void terminate() {
        terminate = true;
    }
}