package org.example.manager;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public record AddressChannelRecord(InetSocketAddress address, DatagramChannel channel) {

}
