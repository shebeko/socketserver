package ru.bis.integration.kase.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.domain.Request;
import ru.bis.integration.kase.domain.RequestType;
import ru.bis.integration.kase.util.KaseConstants;

/**
 * @author shds
 *
 */
@Component(value="unmarshallingService")
public class UnmarshallingService {

    private final static Logger logger = Logger.getLogger(UnmarshallingService.class);

    public Context service(Context ctx) {
        String rqSt = ctx.getMessage().getText();
        logger.info("Getting new incoming message: \"" + rqSt + "\"");
        Request rqObj = UnmarshallingService.createRequest(rqSt);
        logger.info("Request object created: " + rqObj);
        ctx.addProperty(Context.REQUEST_PROPERTY, rqObj);
        return ctx;
    }

    private static Request createRequest(String rqSt) {
        Request rq = null;
        switch (rqSt) {
            case KaseConstants.GET_EXECUTION_REPORTS_REQUEST: {rq = new Request(RequestType.GET_EXECUTION_REPORTS); break;}
            case KaseConstants.GET_MARKET_DATA_REQUEST: {rq= new Request(RequestType.GET_MARKET_DATA); break;}
            default: {rq = new Request(RequestType.UNKNOWN_REQUEST); break;}
        }
        return rq;
    }
}