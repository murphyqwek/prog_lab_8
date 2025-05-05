package org.example.util.hash;

import org.example.base.response.ServerResponseUpdate;
import org.example.manager.UpdateCollectionManager;
import org.example.server.Server;

import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 * SendUpdates - описание класса.
 *
 * @version 1.0
 */

public class SendUpdates {
    public static UpdateCollectionManager updateCollectionManager;
    public static Server server;

    public static void sendUpdates() {
        ServerResponseUpdate serverResponseUpdate = new ServerResponseUpdate();
        DatagramChannel channel;
        try {
            channel = DatagramChannel.open();
        } catch (IOException e) {
            return;
        }

        for(var to_send : updateCollectionManager.getAddresses()) {
            server.send(to_send.channel(), to_send.address(), serverResponseUpdate);
        }
    }
}
