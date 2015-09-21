package ru.bis.integration.kase.fix.client;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import quickfix.ConfigError;
import quickfix.RuntimeError;
import quickfix.SocketInitiator;
import ru.bis.integration.kase.domain.AdapterInfo;
import ru.bis.integration.kase.domain.ServerType;

/**
 * @author shds
 *
 */
public class FixClient {

    private static final Logger logger = Logger.getLogger(FixClient.class);
    private ApplicationImpl app;
    private SocketInitiator initiator;

    @Autowired
    private AdapterInfo info;

    public FixClient() {
    }

    public void setApplication(ApplicationImpl app) {
        this.app = app;
    }

    public void setSocketInitiator(SocketInitiator initiator) {
        this.initiator = initiator;
    }

    public void start() throws RuntimeError, ConfigError, InterruptedException {
        logger.info("Establishing connection to FIX 5.0 server...");
        initiator.start();
        app.awaitLogon();
        logger.info("FixClient is ready to perform requests");
        sendRequests();
    }

    public void stop() throws InterruptedException {
        logger.info("Logout initiated...");
        app.logout();
        app.awaitLogout();
        logger.info("FixClient is stopped");
    }

    public void sendRequests() {
        app.sendOrderStatusRequest();
        if (info.serverType() == ServerType.REPO) {
            app.sendSecurityListRequest();
        }
    }
}