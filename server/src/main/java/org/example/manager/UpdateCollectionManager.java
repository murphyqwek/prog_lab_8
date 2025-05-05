package org.example.manager;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * UpdateCollectionManager - описание класса.
 *
 * @version 1.0
 */

public class UpdateCollectionManager {
    private final CopyOnWriteArrayList<AddressChannelRecord> addresses;

    public UpdateCollectionManager() {
        addresses = new CopyOnWriteArrayList<AddressChannelRecord>();
    }

    public List<AddressChannelRecord> getAddresses() {
        return addresses;
    }

    public void addAddress(InetSocketAddress address, DatagramChannel channel) {
        addresses.add(new AddressChannelRecord(address, channel));

    }

    public void removeAddress(InetSocketAddress address) {
        addresses.removeIf(element -> {
            return element.address().equals(address);
        });
    }
}


