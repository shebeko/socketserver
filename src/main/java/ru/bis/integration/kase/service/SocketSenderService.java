package ru.bis.integration.kase.service;

import java.io.PrintWriter;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.util.KaseConstants;

/**
 * @author shds
 * 
 */
@Component(value="socketSenderService")
public class SocketSenderService {

    private static Logger logger = Logger.getLogger(SocketSenderService.class);

    public void send(Context ctx) {
        String response = ctx.getMessage().getText();
        try (Socket socket = (Socket)ctx.getProperty(Context.TRANSPORT_PROPERTY);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(response + "\n" + KaseConstants.EOC_MARKER + "\n");
            out.flush();
        } catch (Exception e) {
            logger.error(e);
        }
        logger.info("Response \"" + response + "\" was sent back over TCP.");
    }
}