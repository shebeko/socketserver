package ru.bis.integration.kase.transport.exception;

/**
 * @author shds
 *
 */
public class SocketConnectionException extends Exception {
    private static final long serialVersionUID = -6691880916499704228L;

    public SocketConnectionException() {
        super();
    }

    public SocketConnectionException(String message) {
        super(message);
    }

    public SocketConnectionException(Throwable t) {
        super(t);
    }
}