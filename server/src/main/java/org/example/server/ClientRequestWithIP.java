package org.example.server;

import org.example.base.response.ClientCommandRequest;

import java.net.InetSocketAddress;

/**
 * ClientResponseWithIP - дата класс для хранения ip клиента и его запроса
 * @param clientResponse
 * @param ip
 */
public record ClientRequestWithIP(ClientCommandRequest clientResponse, InetSocketAddress ip) {
}
