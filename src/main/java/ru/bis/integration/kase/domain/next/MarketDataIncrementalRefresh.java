package ru.bis.integration.kase.domain.next;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author shds
 *
 */
public class MarketDataIncrementalRefresh {

    public static final int LAST_DAY_BEFORE_AVG_WEIGHTED_PRICE = 5202;

    private List<MarketDataSubMessage> sml;

    @XmlElement(name = "MarketDataSubMessage")
    public List<MarketDataSubMessage> getMarketDataSubMessageList() {
        return sml;
    }

    public void setMarketDataSubMessageList(List<MarketDataSubMessage> sml) {
        this.sml = sml;
    }
}