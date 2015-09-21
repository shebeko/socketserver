package ru.bis.integration.kase.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.bis.integration.kase.domain.AdapterInfo;
import ru.bis.integration.kase.domain.Context;
import ru.bis.integration.kase.domain.Message;
import ru.bis.integration.kase.domain.Mode;
import ru.bis.integration.kase.domain.next.KaseExecutionReport;
import ru.bis.integration.kase.repository.KaseRepository;
import ru.bis.integration.kase.util.Util;

/**
 * @author shds
 *
 */
@Component(value="executionReportsProcessor")
public class ExecutionReportsProcessor {

    private final static Logger logger = Logger.getLogger(ExecutionReportsProcessor.class);

    @Autowired
    KaseRepository repository;

    @Autowired
    AdapterInfo info;

    public Context service(Context ctx) {
        logger.info("Processing existing execution reports...");
        String envelopeXML = null;
        if (info.mode() == Mode.PROD) {
            List<KaseExecutionReport> reports = repository.getExecutionReports();
            envelopeXML = Util.createEnvelopeXML(reports);
        } else {
            envelopeXML = Util.readFile(info.getERResponseFile());
        }
        ctx.setMessage(new Message(envelopeXML));
        return ctx;
    }
}