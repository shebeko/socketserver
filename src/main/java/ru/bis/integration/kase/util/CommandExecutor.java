package ru.bis.integration.kase.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import quickfix.ConfigError;
import ru.bis.integration.kase.adapter.App;
import ru.bis.integration.kase.domain.AdapterInfo;
import ru.bis.integration.kase.domain.Mode;
import ru.bis.integration.kase.fix.client.FixClient;
import ru.bis.integration.kase.transport.CommandSocketListener;
import ru.bis.integration.kase.transport.DocumentSocketListener;
import ru.bis.integration.kase.transport.exception.SocketConnectionException;
import ru.bis.integration.kase.transport.exception.SocketListeningException;
import static ru.bis.integration.kase.util.KaseConstants.*;

/**
 * @author shds
 * 
 */
public class CommandExecutor {
    private final static Logger logger = Logger.getLogger(CommandExecutor.class);

    /**
     * Starts KASE adapter
     */
    public void start() {
        DocumentSocketListener dsl = App.loadApplicationContext().getBean(DocumentSocketListener.class);
        dsl.start();
        await();
        dsl.stop();
        App.loadApplicationContext().close();
    }

    /**
     * Stops KASE adapter
     */
    public void stop() {
        CommandSocketListener csl = App.loadApplicationContext().getBean(
                CommandSocketListener.class);
        int port = csl.getPort();
        Socket socket = new Socket();
        InetSocketAddress addr = new InetSocketAddress(port);
        try {
            socket.connect(addr);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));
            bw.write(SHUTDOWN_COMMAND_MSG + System.getProperty("line.separator") + EOC_MARKER);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            App.loadApplicationContext().close();
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean status() {
        CommandSocketListener csl = App.loadApplicationContext().getBean(
                CommandSocketListener.class);
        boolean isRunning = false;
        Socket socket = new Socket();
        InetSocketAddress addr = new InetSocketAddress(csl.getPort());
        try {
            socket.connect(addr);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));
            bw.write(STATUS_COMMAND_MSG + System.getProperty("line.separator") + EOC_MARKER);
            bw.flush();
            socket.shutdownOutput();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String reply = br.readLine();
            if (reply.equals(MSG_RUNNING))
                isRunning = true;
            bw.close();
            br.close();
        } catch (Exception e) {
            // do something
            // e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                // do something
                // e.printStackTrace();
            }
        }
        System.out.println("Checking adapter status...");
        System.out.println(isRunning ? "KASE adapter is running"
                : "KASE adapter is not running");
        App.loadApplicationContext().close();
        return isRunning;
    }

    public boolean check() {
        boolean chckResult = true;
        CommandSocketListener csl = App.loadApplicationContext().getBean(
                CommandSocketListener.class);
        try {
            csl.connect();
            csl.disconnect();
        } catch (SocketConnectionException e) {
            chckResult = false;
        } finally {
            System.out.println("Checking if port " + csl.getPort()
                    + " is available...");
            System.out.println(chckResult ? "Port is free" : "Port is busy");
        }
        return chckResult;
    }

    /**
     * Starts CommandSocketListener listening to commands to KASE adapter
     */
    private void await() {
        ApplicationContext ctx = App.loadApplicationContext();
        CommandSocketListener csl = ctx.getBean(CommandSocketListener.class);
        AdapterInfo info = ctx.getBean(AdapterInfo.class);
        try {
            csl.connect();
            if (info.mode() == Mode.PROD) {
                ctx.getBean(FixClient.class).start();
            }
            csl.listen();
        } catch (SocketConnectionException sce) {
            logger.error(sce);
            sce.printStackTrace();
        } catch (SocketListeningException sle) {
            logger.error(sle);
            sle.printStackTrace();
        } catch (InterruptedException|ConfigError ie) {
            logger.error(ie);
        }
        finally {
            try {
                csl.disconnect();
            } catch (SocketConnectionException sce) {
                sce.printStackTrace();
            }
        }
    }

    /**
     * Prints out KASE adapter usage info
     */
    public void printUsage() {
        StringBuffer sb = new StringBuffer();
        sb.append("Usage: java -cp <classpath> ru.bis.integration.kase.adapter.App <option> \n");
        sb.append("\t where options include: \n");
        sb.append("\t\t start \t\t\t - start adapter \n");
        sb.append("\t\t stop \t\t\t - stop adapter \n");
        sb.append("\t\t status \t\t - show adapter status");
        System.out.println(sb.toString());
    }

    public void exit() {
        System.exit(-1);
    }
}