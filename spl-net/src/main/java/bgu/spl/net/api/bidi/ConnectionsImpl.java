package bgu.spl.net.api.bidi;

import bgu.spl.net.srv.bidi.ConnectionHandler;
import com.sun.jdi.connect.spi.Connection;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsImpl<T>  implements Connections<T> {
   private ConcurrentHashMap<Integer, ConnectionHandler<T>> connectionsMap;


    public ConnectionsImpl(){
        connectionsMap = new ConcurrentHashMap<>();
    }


    public void add(int id, ConnectionHandler toAdd){
        connectionsMap.put(id,toAdd);
    }

    @Override
    public boolean send(int connectionId, T msg) {
        // to check out wtf ?
        if(connectionsMap.get(connectionId) != null) {
            connectionsMap.get(connectionId).send(msg);
            //should it be syncronized?
            return true;
        }
        return false;
    }

    @Override
    public void broadcast(T msg) {
        synchronized (connectionsMap){
            for(Integer i : connectionsMap.keySet()){
                connectionsMap.get(i).send(msg);
            }
        }
    }

    @Override
    public void disconnect(int connectionId) {
        connectionsMap.remove(connectionId);
    }
}
