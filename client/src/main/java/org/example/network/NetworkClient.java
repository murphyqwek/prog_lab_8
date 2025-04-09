package org.example.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UserLoginPasswordContainer;
import org.example.base.response.ClientCommandRequest;
import org.example.base.response.ClientCommandRequestWithLoginAndPassword;
import org.example.base.response.ServerResponse;
import org.example.base.response.ServerResponseType;
import org.example.exception.CouldnotConnectException;
import org.example.exception.InvalidPortException;
import org.example.exception.SerializationException;
import org.example.exception.ServerErrorResponseExcpetion;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeoutException;

/**
 * NetworkClient - класс для общения с сервером.
 *
 * @version 1.0
 */

public class NetworkClient {
    private final Logger logger = LogManager.getRootLogger();
    private final InetSocketAddress serverAddress;
    private final DatagramChannel channel;
    private UserLoginPasswordContainer userLoginPasswordContainer;

    public NetworkClient(String ip, int port) {
        this(ip, port, new UserLoginPasswordContainer());
    }

    /**
     * Конструктор класса
     * @param ip адрес сервера
     * @param port порт подключения
     * @throws CouldnotConnectException если не удалось подключиться к серверу
     */
    public NetworkClient(String ip, int port, UserLoginPasswordContainer userLoginPasswordContainer) throws CouldnotConnectException {
        this.serverAddress = new InetSocketAddress(ip, port);
        this.userLoginPasswordContainer = userLoginPasswordContainer;

        try {
            this.channel = ClientConnection.connect(this.serverAddress);
        } catch (SocketException e) {
            throw new CouldnotConnectException(e.getMessage());
        } catch (InvalidPortException e) {
            throw new CouldnotConnectException("Неверный порт");
        }
    }

    public void sendUserCommand(ClientCommandRequest clientCommandRequest) {
        ClientCommandRequestWithLoginAndPassword request = new ClientCommandRequestWithLoginAndPassword(clientCommandRequest, userLoginPasswordContainer.getLogin(), userLoginPasswordContainer.getPassword());
        NetworkSender.sendUserCommand(request, channel);
    }

    public void setUserLoginPasswordContainer(UserLoginPasswordContainer userLoginPasswordContainer) {
        if(userLoginPasswordContainer == null) {
            throw new NullPointerException("userLoginPasswordContainer is null");
        }

        this.userLoginPasswordContainer = userLoginPasswordContainer;
    }

    /**
     * Метод для получения ответа сервера
     */
    public ServerResponse getServerResponse() throws ServerErrorResponseExcpetion {
        try {
            var buffer = NetworkReceiver.receiveBuffer(this.channel, 500, 10);

            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
            ObjectInputStream ois = new ObjectInputStream(bais);

            ServerResponse serverResponse = (ServerResponse) ois.readObject();

            if(serverResponse.getType() == ServerResponseType.ERROR) {
                logger.warn("Ответ сервера содержит ошибку");
                logger.warn(serverResponse.toString());
                throw new ServerErrorResponseExcpetion(serverResponse.getMessage(), false);
            }

            if(serverResponse.getType() == ServerResponseType.CORRUPTED) {
                logger.warn("Ответ сервера пришел поврежденным");
                throw new ServerErrorResponseExcpetion("Ответ сервера пришел поврежденным", false);
            }

            if(serverResponse.getType() == ServerResponseType.UNAUTHORIZED) {
                logger.warn("Неавторизованный вход");
                throw new ServerErrorResponseExcpetion("Пользователь не авторизовался на сервере", true);
            }


            logger.info(serverResponse.toString());
            return serverResponse;
        }
        catch (IOException e) {
            logger.error("Не удалось получить ответ от сервера");
            throw new ServerErrorResponseExcpetion("Не удалось получить ответ от сервера", true);
        }
        catch (InterruptedException ex) {
            logger.error(ex.getMessage());
            throw new ServerErrorResponseExcpetion(ex.getMessage(), true);
        }
        catch (TimeoutException ex) {
            logger.error("Timeout - сервер не отвечает");
            throw new ServerErrorResponseExcpetion("Timeout - сервер не отвечает", true);
        }
        catch (ClassNotFoundException ex) {
            logger.error("Сервер вернул неожиданный ответ");
            throw new ServerErrorResponseExcpetion("Сервер вернул неожиданный ответ", true);
        }
    }
}
