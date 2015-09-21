package ru.bis.integration.kase.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.bis.integration.kase.domain.AdapterInfo;
import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.domain.Message;
import ru.bis.integration.kase.domain.Mode;
import ru.bis.integration.kase.domain.ServerType;
import ru.bis.integration.kase.domain.next.MarketDataIncrementalRefresh;
import ru.bis.integration.kase.repository.KaseRepository;
import ru.bis.integration.kase.util.Util;

/**
 * @author shds
 *
 */
@Component(value="marketDataProcessor")
public class MarketDataProcessor {

    private static final Logger logger  = Logger.getLogger(Logger.class);

    @Autowired
    KaseRepository repository;

    @Autowired
    AdapterInfo info;

    public Context service(Context ctx) {
        logger.info("Processing existing market data...");
        String text = null;
        if (info.mode() == Mode.PROD && info.serverType() == ServerType.REPO) {
            List<MarketDataIncrementalRefresh> marketData = repository.getMarketData();
            text = Util.createEnvelopeXML(marketData);
        }
        if (info.mode() == Mode.PROD && info.serverType() == ServerType.CUR) {
            text = "Market data is not used for server.type set to CUR. Switch server.type property to REPO to get existing market data";
        }
        if (info.mode() == Mode.TEST) {
            text = "Market data unsupported in TEST mode";
        }
        ctx.setMessage(new Message(text));
        return ctx;
    }
}