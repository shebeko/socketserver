package ru.bis.integration.kase.transport;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.transport.exception.SocketConnectionException;
import ru.bis.integration.kase.transport.exception.SocketListeningException;

/**
 * @author shds
 *
 */
public abstract class AbstractSocketListener implements SocketListener, Runnable {
    private static final Logger logger = Logger.getLogger(AbstractSocketListener.class);
    private int port;
    protected ServerSocket serverSocket;
    private volatile boolean isListening;

    public AbstractSocketListener(int port) {
        this.port = port;
        isListening = true;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void connect() throws SocketConnectionException {
        try {
            logger.info("Starting " + this.getClass().getSimpleName() + "...");
            serverSocket = new ServerSocket(port);
            logger.info(this.getClass().getSimpleName() + " started succesfully on port " + port);
        } catch (IOException e) {
              logger.error("Failed to start " + this.getClass().getSimpleName() + " on port " + port, e);
              throw new SocketConnectionException(e);
        }
    }

    @Override
    public void disconnect() throws SocketConnectionException {
        try {
            logger.info("Stopping " + this.getClass().getSimpleName() + ".");
            if (serverSocket != null)
                serverSocket.close();
            logger.info(this.getClass().getSimpleName() + " stopped.");
        } catch (IOException e) {
            logger.error("Failed to stop" + this.getClass().getSimpleName() + " on port " + port, e);
            throw new SocketConnectionException(e);
        }
    }

    /**
     * Hook method, may be overridden in subclasses
     */
    @Override
    public void dispatch(Context context) {}

    /**
     *  Starts socket listener in separate thread
     */
    @Override
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops socket listener thread if running
     */
    @Override
    public void stop() {
        isListening = false;
    }

    public String info() {
        return "SocketListener: " + this.getClass().getSimpleName() + ", Port: " + port;
    }

    @Override
    public void run() {
        // shutdown if stopped
        if (!isListening)
            return;
        try {
            connect();
            // listen to socket listener's port
            while (isListening) {
                try {
                    Context ctx = listen();
                    if (ctx.getMessage() != null) {
                        dispatch(ctx);
                    }
                } catch (SocketListeningException e) {
                    logger.error(e);
                }
            }
        } catch (SocketConnectionException sce) {
            sce.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SocketConnectionException sce) {
                sce.printStackTrace();
            }
        }
    }
}