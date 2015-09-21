package ru.bis.integration.kase.fix.client;

import quickfix.*;
import ru.bis.integration.kase.repository.KaseRepository;
import ru.bis.integration.kase.util.KaseMessageConverter;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import kz.kase.fix.EncryptMethod;
import kz.kase.fix.FixProtocol;
import kz.kase.fix.SecurityListRequestType;
import kz.kase.fix.SubscriptionType;
import kz.kase.fix.messages.ExecutionReport;
import kz.kase.fix.messages.Heartbeat;
import kz.kase.fix.messages.Logon;
import kz.kase.fix.messages.Logout;
import kz.kase.fix.messages.MDIncRefresh;
import kz.kase.fix.messages.MarketDataRequest;
import kz.kase.fix.messages.OrderStatusRequest;
import kz.kase.fix.messages.SecurityList;
import kz.kase.fix.messages.SecurityListRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationImpl implements Application {

    public static final int FIELD_ORDER_SERIAL = 5181;
    public static final int FIELD_DEAL_SERIAL = 5185;
    public static final int FIELD_CASH_QTY = 152;
    public static final int FIELD_TRANSACTION_TIME = 60;
    public static final int FIELD_ACCOUNT_NAME = 448;
    public static final int FIELD_ORDER_ID = 37;
    public static final int FIELD_USERNAME = 553;
    public static final int FIELD_SETTLEMENT_DATE = 64;
    public static final int FIELD_NIN = 5032;
    public static final int REF_RAND_SEED = 100000;
    public static final String PASS = "Password";
    public static final String USERNAME = "Username";
    public static final String DEALS_FOLDER = "deals/";
    public static final long delta = 30000L;

    private final CountDownLatch logonLatch = new CountDownLatch(1);
    private final CountDownLatch logoutLatch = new CountDownLatch(1);

    @Autowired
    SocketInitiator initiator;

    @Autowired
    KaseRepository repository;

    private final SessionSettings settings;

    private Session session;

    private static Logger logger = Logger.getLogger(ApplicationImpl.class);
    private static Logger heartbeatLogger = Logger.getLogger("ru.bis.heartbeat");

    public ApplicationImpl(SessionSettings settings) {
        super();
        this.settings = settings;
    }

    public SessionSettings getSessionSettings() {
        return settings;
    }

    @Override
    public void onCreate(SessionID sessionID) {
    }

    @Override
    public void onLogon(SessionID sessionID) {
        logger.info("Logon completed. SessionID = " + sessionID);
        this.session = Session.lookupSession(sessionID);
        logonLatch.countDown();
    }

    @Override
    public void onLogout(SessionID sessionID) {
        logger.info("Logout completed. sessionID = " + sessionID);
        logger.info("Closing connection to FIX 5.0 server...");
        initiator.stop();
        logoutLatch.countDown();
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        if (message instanceof Logout) {
            Logout logout = (Logout) message;
            logout.setString(1137, "7");
            logger.info("Logout message was sent to KASE: " + System.getProperty("line.separator") + logout);
        }
        if (message instanceof Logon) {
            Logon logon = (Logon) message;
            logon.setUsername(sessionID.getSenderCompID());
            try {
                logon.setPassword(settings.getString(PASS));
            } catch (ConfigError | FieldConvertError configError) {
                configError.printStackTrace();
                logger.error(configError);
            }
            logon.setEncryptMethod(EncryptMethod.NONE_OTHER);
            logon.setResetSeqNum(true);
            logger.info("Logon message was sent to KASE: " + System.getProperty("line.separator") + logon);
        }
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID)
            throws IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        if (!(message instanceof Heartbeat)) {
            logger.info("Received session message " + message.getClass().getName() + System.getProperty("line.separator") + message);
        } else {
            if (heartbeatLogger.isDebugEnabled()) {
                if (message instanceof Heartbeat) {
                    heartbeatLogger.debug(message);
                }
            }
        }
    }

    @Override
    public void toApp(Message message, SessionID sessionID) {
        if (message instanceof OrderStatusRequest) {
            logger.info("OrderStatusRequest message was sent to FIX server: " + System.getProperty("line.separator") + message);
        }
        if (message instanceof MarketDataRequest) {
            logger.info("MarketDataRequest message was sent to FIX server:" + System.getProperty("line.separator") + message);
        }
        if (message instanceof SecurityListRequest) {
            logger.info("SecurityListRequest message was sent to FIX server:" + System.getProperty("line.separator") + message);
        }
    }

    @Override
    public void fromApp(Message message, SessionID sessionID)
            throws IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        if (message instanceof ExecutionReport) {
            handleExecutionReport((ExecutionReport)message, sessionID);
        }
        if (message instanceof MDIncRefresh) {
            handleMarketDataIncrementalRefresh((MDIncRefresh)message, sessionID);
        }
        if (message instanceof SecurityList) {
            handleSecurityList((SecurityList)message, sessionID);
        }
    }

    public void awaitLogon() throws InterruptedException {
        logonLatch.await();
    }

    public void awaitLogout() throws InterruptedException {
        logoutLatch.await();
    }

    public void sendMessage(Message message) {
        if (session != null) {
            session.send(message);
        }
    }

    public void handleExecutionReport(ExecutionReport msg, SessionID sessionID) {
        logger.info("New ExecutionReport with ExecID=" + msg.getExecId() + " and OrderID=" + msg.getOrderId() +  " received");
        repository.addExecutionReport(KaseMessageConverter.convert(msg));
    }

    public void handleMarketDataIncrementalRefresh(MDIncRefresh msg, SessionID sessionID) {
        logger.info("New MarketDataIncrementalRefresh received: " + msg);
        repository.addMarketDataIncrementalRefresh(KaseMessageConverter.convert(msg));
    }

    public void handleSecurityList(SecurityList msg, SessionID sessionID) {
        logger.info("New SecurityList received: " + msg);
        List<Group> secLst = msg.getGroups(FixProtocol.FIELD_NO_RELATED_SYM);
        for (Group secGrp : secLst) {
                String symbol = secGrp.getString(FixProtocol.FIELD_SYMBOL);
                repository.addSymbol(symbol);
        }
        logger.info("Symbols in SecurityList: " + repository.getSymbols());
        sendMarketDataRequest();
    }

    public void sendOrderStatusRequest() {
        logger.info("Sending order status request...");
        OrderStatusRequest request = new OrderStatusRequest();
        request.setRef(ApplicationImpl.nextRef());
        sendMessage(request);
    }

    public void sendMarketDataRequest() {
        logger.info("Sending market data request...");
        MarketDataRequest request = new MarketDataRequest(false);
        request.setRef(nextRef());
        for (String symbol: repository.getSymbols()) {
            request.addInstrument(symbol);
        }
        request.setSubscriptionType(SubscriptionType.SNAPSHOT_AND_UPDATES);
        sendMessage(request);
    }

    public void sendSecurityListRequest() {
        logger.info("Sending security list request...");
        SecurityListRequest secReq = new SecurityListRequest();
        secReq.setRef(nextRef());
        secReq.setType(SecurityListRequestType.ALL_SECURITIES);
        sendMessage(secReq);
    }

    public void logout() {
        if (session != null) {
            session.logout();
        }
    }

    public static long nextRef() {
        return new Random(System.currentTimeMillis()).nextInt(REF_RAND_SEED);
    }

    public static String nextRefStr() {
        return String.valueOf(nextRef());
    }
}