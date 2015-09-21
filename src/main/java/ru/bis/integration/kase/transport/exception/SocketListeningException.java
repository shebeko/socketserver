package ru.bis.integration.kase.transport.exception;

/**
 * @author shds
 *
 */
public class SocketListeningException extends Exception {
    private static final long serialVersionUID = 806504781772640522L;

    public SocketListeningException() {
        super();
    }

    public SocketListeningException(String message) {
        super(message);
    }

    public SocketListeningException(Throwable t) {
        super(t);
    }
}