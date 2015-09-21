package ru.bis.integration.kase.transport;

import static ru.bis.integration.kase.util.KaseConstants.EOC_MARKER;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;

import ru.bis.integration.kase.adapter.App;
import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.transport.exception.SocketConnectionException;
import ru.bis.integration.kase.transport.exception.SocketListeningException;

/**
 * @author shds
 * 
 */
public class DocumentSocketListener extends AbstractSocketListener {
    private static Logger logger = Logger.getLogger(DocumentSocketListener.class);

    public DocumentSocketListener(int port) {
        super(port);
    }

    @Override
    public void connect() throws SocketConnectionException {
        super.connect();
        try {
            serverSocket.setSoTimeout(2000);
        } catch (SocketException e) {
            logger.error(e);
        }
    }

    @Override
    public Context listen() throws SocketListeningException {
        Context context = new Context();
        try {
                Socket socket = serverSocket.accept();
                context.addProperty(Context.TRANSPORT_PROPERTY, socket);
                String incomingRq = getSocketBytes(socket, "cp1251");
                socket.shutdownInput();
                context.setMessage(new ru.bis.integration.kase.domain.Message(incomingRq));
        } catch (SocketTimeoutException e) {
            // Timeout is exceeded. Let the adapter catch halt.
            //e.printStackTrace();
            // if (logger.isTraceEnabled())
            // logger.trace("Listener is pending. No new messages received.",
            // e);
        } catch (IOException e) {
            // logger.warn("Failed reading socket stream.", e);
            e.printStackTrace();
            throw new SocketListeningException(e);
        }
        return context;
    }

    // dispatches context to queue channel
    @Override
    public void dispatch(Context ctx) {
        QueueChannel channel = (QueueChannel)App.loadApplicationContext().getBean("documentInputChannel");
        Message<Context> ctxMessage = MessageBuilder.withPayload(ctx).build();
        channel.send(ctxMessage);
    }

    private String getSocketBytes(Socket socket, String enc) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), enc));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            if (line.contains(EOC_MARKER)) {
                sb.append(line.replace(EOC_MARKER, ""));
                break;
            }
            sb.append(line);
        }
        return sb.toString();
    }
}