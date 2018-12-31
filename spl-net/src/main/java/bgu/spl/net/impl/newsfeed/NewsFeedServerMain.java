package bgu.spl.net.impl.newsfeed;

import bgu.spl.net.api.bidi.BGSystem;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.api.bidi.MessageEncoderDecoderImpl;
import bgu.spl.net.impl.rci.ObjectEncoderDecoder;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
import bgu.spl.net.srv.Server;

public class NewsFeedServerMain {

    public static void main(String[] args) {
        NewsFeed feed = new NewsFeed();//one shared object
        BGSystem data = new BGSystem();
// you can use any server...
    /*    Server.threadPerClient(
               7777, //port
              () -> new BidiMessagingProtocolImpl(data) {
              }, //protocol factory
               MessageEncoderDecoderImpl::new //message encoder decoder factory
       ).serve();*/

       Server.reactor(
                Runtime.getRuntime().availableProcessors(),
                7777, //port
                () ->  new BidiMessagingProtocolImpl(data) {
                }, //protocol factory
               MessageEncoderDecoderImpl::new //message encoder decoder factory
        ).serve();

    }
}
