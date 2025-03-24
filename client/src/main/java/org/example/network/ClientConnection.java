package org.example.network;

import org.example.exception.InvalidPortException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

/**
 * ClientConnection - класс для подключения к серверу.
 *
 * @version 1.0
 */

public class ClientConnection {

    /**
     * Метод для подключения к серверу
     * @param serverAddress адрес сервера
     * @return DatagramChannel если удалось соединиться
     * @throws InvalidPortException если порт указан неверный
     * @throws SocketException если не удалось подключиться к серверу
     */
    public static DatagramChannel connect(InetSocketAddress serverAddress) throws InvalidPortException, SocketException {
        try{
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.connect(serverAddress);
            return channel;
        }
        catch(IllegalArgumentException ex) {
            throw new InvalidPortException();
        }
        catch (SocketException e) {
            throw e;
        }
        catch (IOException ex) {
            throw new SocketException();
        }
    }
}
