package ru.bis.integration.kase.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ru.bis.integration.kase.domain.next.KaseExecutionReport;
import ru.bis.integration.kase.domain.next.MarketDataIncrementalRefresh;

/**
 * @author shds
 *
 */
@XmlRootElement(name="Envelope")
public class Envelope {

    private List<KaseExecutionReport> reports;

    private List<MarketDataIncrementalRefresh> marketData;

    public void setExecutionReports(List<KaseExecutionReport> reports) {
        this.reports = reports;
    }

    @XmlElementWrapper(name = "ExecutionReports")
    @XmlElement(name = "ExecutionReport")
    public List<KaseExecutionReport> getExecutionReports() {
        return reports;
    }

    public void setMarketData(List<MarketDataIncrementalRefresh> marketData) {
        this.marketData = marketData;
    }

    @XmlElementWrapper(name="MarketData")
    @XmlElement(name="MarketDataIncrementalRefresh")
    public List<MarketDataIncrementalRefresh> getMarketData() {
        return marketData;
    }
}