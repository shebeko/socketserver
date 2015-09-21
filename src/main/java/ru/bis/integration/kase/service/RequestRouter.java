package ru.bis.integration.kase.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.domain.Request;
import ru.bis.integration.kase.util.KaseConstants;

/**
 * @author shds
 *
 */
@Component(value="requestRouter")
public class RequestRouter {

    public static final Logger logger = Logger.getLogger(RequestRouter.class);

    public String processRequest(Context ctx) {
        Request req = (Request)ctx.getProperty(Context.REQUEST_PROPERTY);
        logger.info("Routing " + req +  " ...");
        switch (req.getType()) {
            case GET_EXECUTION_REPORTS: {return "executionReports";}
            case GET_MARKET_DATA: {return "marketData";}
            default: {
                ctx.getMessage().setText(KaseConstants.UNKNOWN_REQUEST_RECEIVED + ctx.getMessage().getText());
                return "documentOutputChannel";
            }
        }
    }
}