package ru.bis.integration.kase.transport;

import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.transport.exception.SocketConnectionException;
import ru.bis.integration.kase.transport.exception.SocketListeningException;

/**
 * @author shds
 *
 */
public interface SocketListener extends Launchable {
    void connect() throws SocketConnectionException;
    void disconnect() throws SocketConnectionException;
    Context listen() throws SocketListeningException;
    void dispatch(Context context);
    String info();
}