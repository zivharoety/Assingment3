package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.MessageEncoderDecoderImpl;

import java.util.function.Supplier;

public class threadPerClient<T> extends BaseServer<T> {

        public threadPerClient( int port,
                                      Supplier<BidiMessagingProtocol<T>> protocolFactory,
                                      Supplier<MessageEncoderDecoder<T>> encdecFactory){

            super(port,protocolFactory,encdecFactory);

        }// to make it static !
        @Override
        protected void execute(BlockingConnectionHandler<T> handler) {
            Thread thread = new Thread(handler);
            thread.start();
        }
    }


