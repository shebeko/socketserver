package ru.bis.integration.kase.transport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import ru.bis.integration.kase.adapter.App;
import ru.bis.integration.kase.domain.AdapterInfo;
import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.domain.Mode;
import ru.bis.integration.kase.fix.client.FixClient;
import ru.bis.integration.kase.transport.exception.SocketListeningException;
import static ru.bis.integration.kase.util.KaseConstants.*;

/**
 * @author shds
 *
 */
public class CommandSocketListener extends AbstractSocketListener {

    private final static Logger logger = Logger.getLogger(CommandSocketListener.class);

    public CommandSocketListener(int port) {
        super(port);
    }

    @Override
    public Context listen() throws SocketListeningException {
        Context context = new Context();
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                String command = getSocketBytes(socket, "cp1251");
                socket.shutdownInput();
                if (command.equals(SHUTDOWN_COMMAND_MSG)) {
                    logger.warn("CommandSocketListener received command SHUTDOWN");
                    ApplicationContext ctx = App.loadApplicationContext();
                    AdapterInfo info = ctx.getBean(AdapterInfo.class);
                    if (info.mode() == Mode.PROD) {
                        ctx.getBean(FixClient.class).stop();
                    }
                    break;
                }
                if (command.equals(STATUS_COMMAND_MSG)) {
                    logger.warn("CommandSocketListener received command STATUS");
                    BufferedWriter bw = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream()));
                    bw.write(MSG_RUNNING + "\n" + EOC_MARKER + "\n");
                    bw.flush();
                    bw.close();
                }
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            logger.error(ie);
        } catch (IOException e) {
            logger.error("Failed to read/write from/to socket stream", e);
            e.printStackTrace();
            throw new SocketListeningException(e);
        }
        return context;
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