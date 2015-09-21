package ru.bis.integration.kase.domain.next;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * For more details, see http://next.kase.kz/fixspec/
 * @author shds
 *
 */
@XmlRootElement
public class MarketDataSubMessage {
    private Character mdUpdateAction; // 279, 0 = New [New], 1 = Change[Change], 2 = Delete [Delete]
    private String symbol; // 55, USDKZT_TOM
    private String lastPx; // 31 цена последней сделки
    private Long lastQty; // 32 объем последней сделки
    private Character mdEntryType; // 269, возможные значения 0 (BID) 1 (OFFER) 7(TRADING_SESSION_HIGH), 8(TRADING_SESSION_LOW)
    private String mdEntrySize; // 271, размер элемента
    private String mdEntryPrice; // 270,  цена
    private String lastDealBeforeTodayPrice; // 5106, например, 5106=0.07
    private String lastDealBeforeTodayVolume; // 5107, например, 5107=20000000
    private Date lastDealBeforeTodayTime; // 5115
    private String lastDayAvgPrice; // 5157
    private Integer tradeSessionId; // 336
    private Integer numberOfOrders; // 346 кол-во заявок в течении торговой сессии
    private String priceDelta; // 811 изменение цены
    private String tradeVolume; // 1020
    private Integer dealsCount; // 5067
    private String dealsVolume; // 5068 объем торгов в контр-валюте
    private Long dealsQtyTotal; // 5069 объем торгов в инструментах
    private String averageWeightedPrice; // 5116
    private String lastDayBeforeAvgWeightedPrice; // 5202 средневзвешенная цена пред дня

    @XmlElement(name="MDUpdateAction")
    public Character getMdUpdateAction() {
        return mdUpdateAction;
    }

    public void setMdUpdateAction(Character mdUpdateAction) {
        this.mdUpdateAction = mdUpdateAction;
    }

    @XmlElement(name="Symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @XmlElement(name="LastPx")
    public String getLastPx() {
        return lastPx;
    }

    public void setLastPx(String lastPx) {
        this.lastPx = lastPx;
    }

    @XmlElement(name="MDEntryType")
    public Character getMdEntryType() {
        return mdEntryType;
    }

    public void setMdEntryType(Character mdEntryType) {
        this.mdEntryType = mdEntryType;
    }

    @XmlElement(name="MDEntrySize")
    public String getMDEntrySize() {
        return mdEntrySize;
    }

    public void setMDEntrySize(String mDEntrySize) {
        mdEntrySize = mDEntrySize;
    }

    public String getMdEntryPrice() {
        return mdEntryPrice;
    }

    @XmlElement(name="MDEntryPrice")
    public void setMdEntryPrice(String mdEntryPrice) {
        this.mdEntryPrice = mdEntryPrice;
    }

    @XmlElement(name="LastQty")
    public Long getLastQty() {
        return lastQty;
    }

    public void setLastQty(Long lastQty) {
        this.lastQty = lastQty;
    }

    @XmlElement(name="LastDealBeforeTodayPrice")
    public String getLastDealBeforeTodayPrice() {
        return lastDealBeforeTodayPrice;
    }

    public void setLastDealBeforeTodayPrice(String lastDealBeforeTodayPrice) {
        this.lastDealBeforeTodayPrice = lastDealBeforeTodayPrice;
    }

    @XmlElement(name="LastDealBeforeTodayVolume")
    public String getLastDealBeforeTodayVolume() {
        return lastDealBeforeTodayVolume;
    }

    public void setLastDealBeforeTodayVolume(String lastDealBeforeTodayVolume) {
        this.lastDealBeforeTodayVolume = lastDealBeforeTodayVolume;
    }

    @XmlElement(name="LastDealBeforeTodayTime")
    public Date getLastDealBeforeTodayTime() {
        return lastDealBeforeTodayTime;
    }

    public void setLastDealBeforeTodayTime(Date lastDealBeforeTodayTime) {
        this.lastDealBeforeTodayTime = lastDealBeforeTodayTime;
    }

    @XmlElement(name="LastDayAvgPrice")
    public String getLastDayAvgPrice() {
        return lastDayAvgPrice;
    }

    public void setLastDayAvgPrice(String lastDayAvgPrice) {
        this.lastDayAvgPrice = lastDayAvgPrice;
    }
    
    @XmlElement(name="TradeSessionId")
    public Integer getTradeSessionId() {
        return tradeSessionId;
    }

    public void setTradeSessionId(Integer tradeSessionId) {
        this.tradeSessionId = tradeSessionId;
    }

    @XmlElement(name="NumberOfOrders")
    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    @XmlElement(name="PriceDelta")
    public String getPriceDelta() {
        return priceDelta;
    }

    public void setPriceDelta(String priceDelta) {
        this.priceDelta = priceDelta;
    }

    @XmlElement(name="TradeVolume")
    public String getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(String tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    @XmlElement(name="DealsCount")
    public Integer getDealsCount() {
        return dealsCount;
    }

    public void setDealsCount(Integer dealsCount) {
        this.dealsCount = dealsCount;
    }

    @XmlElement(name="DealsVolume")
    public String getDealsVolume() {
        return dealsVolume;
    }

    public void setDealsVolume(String dealsVolume) {
        this.dealsVolume = dealsVolume;
    }

    @XmlElement(name="DealsQtyTotal")
    public Long getDealsQtyTotal() {
        return dealsQtyTotal;
    }

    public void setDealsQtyTotal(Long dealsQtyTotal) {
        this.dealsQtyTotal = dealsQtyTotal;
    }

    @XmlElement(name="AverageWeightedPrice")
    public String getAverageWeightedPrice() {
        return averageWeightedPrice;
    }

    public void setAverageWeightedPrice(String averageWeightedPrice) {
        this.averageWeightedPrice = averageWeightedPrice;
    }

    @XmlElement(name="LastDayBeforeAvgWeightedPrice")
    public String getLastDayBeforeAvgWeightedPrice() {
        return lastDayBeforeAvgWeightedPrice;
    }

    public void setLastDayBeforeAvgWeightedPrice(String lastDayBeforeAvgWeightedPrice) {
        this.lastDayBeforeAvgWeightedPrice = lastDayBeforeAvgWeightedPrice;
    }
}