package ru.bis.integration.kase.util;

import org.apache.log4j.Logger;
import ru.bis.integration.kase.domain.Envelope;
import ru.bis.integration.kase.domain.next.KaseExecutionReport;
import ru.bis.integration.kase.domain.next.MarketDataIncrementalRefresh;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

/**
 * @author shds
 *
 */
public class Util {

    private final static Logger logger = Logger.getLogger(Util.class);

    @SuppressWarnings("unchecked")
    public static <T> String createEnvelopeXML(List<T> list) {
        Envelope env = new Envelope();
        if (list != null) {
            Iterator<T> it = list.iterator();
            if (it.hasNext()) {
                T obj = it.next();
                if (obj != null) {
                    if (obj.getClass() == KaseExecutionReport.class) {
                       env.setExecutionReports((List<KaseExecutionReport>)list);
                    } else if (obj.getClass() == MarketDataIncrementalRefresh.class) {
                       env.setMarketData((List<MarketDataIncrementalRefresh>) list);
                    }
                }
            }
        }
        return KaseMarshaller.marshal(env);
    }

    public static String readFile(String filename) {
        String content = "";
        try {
            content = new String(readAllBytes(get(filename)));
        } catch (IOException ex) {
            logger.error(ex);
            content = ex.toString();
        }
        return content;
    }
}