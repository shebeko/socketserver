package ru.bis.integration.kase.domain.next;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * For more details, see
 * https://fixspec.com/FIX50/ExecutionReport
 * and http://next.kase.kz/fixspec/
 * @author shds
 *
 */
@XmlRootElement(name="ExecutionReport")
public class KaseExecutionReport {
    private String account=""; // 1 �������� ����
    private String avgPx=""; // 6 ������=0
    private String clOrdID=""; // 11   ��������� ��������
    private Long cumQty; // 14 ����������� ���-�� � ������
    private String execID=""; // 17 ����� ������
    private String orderID=""; // 37 ����� ������
    private Integer orderQty; // 38 ���������� ������������ � ������
    private Character ordStatus; // 39 ��������� ������,
    // ��������� ������
    //    �0� � ������� � ����������
    //    �1� � ����. �������������
    //    �2� � �������������
    //    �4� � ��������
    //    �5� � ��������
    //    �8� � ��������� ��������
    //    �C� � �������
    private Character ordType; // 40 ��� ������,
    //    ��� ������
    //    �1� � ��������
    //    �2� � ��������������
    //    �3� � ����-����
    //    �4� � ����-�����
    //    �G� � ����
    //    �R� � ����
    private String price=""; // 44 ����, �������� 184.75
    private Character side; // 54 ������� ������/������, '1' - �������, '2' - �������
    private String symbol=""; // 55  ���������� ����������  (�� hexadecimal)
    private String text; // 58  comment/�����������
    private Character timeInForce; // 59 ��� ����������,
    //    ��� ����������
    //    �0� � � ������� ���
    //    �1� � �� ������
    //    �4� � ������������ ����������
    //    �6� � �� ���� ���������
    private Date transactTime; // 60 ����� ����������
    private Character execType; // 150 ��� ������,
    //    ��� ������
    //    �I� � ������
    //    �F� � ������
    //    �8� � ����������� ������
    private Long leavesQty; // 151 ���������� ���-�� � ������
    private String cashOrderQty=""; // 152 ����� ������/������ � �����
    private String tradingSessionID=""; // 336 ����� �������� ������
    private String type; // 5188, ��� ������, ������� ��������: REGULAR_DEAL/SWAP_DEAL/SWAP_LEG_DEAL
    // ����, �� ��������� � ��������� �������, �� �������������� � draft-������������
    private Integer lastQty; // 32 ����� ��������� ������ � ���������� �����������
    private String lastPx; // 31 ���� ����������� ������ � ������ ???????????????????????????
    private Date effectiveTime; // 168 ���� ������� � ��
    private String yield; // 236 ����� �� ����������
    private String orderRestrictions; // 529 �������������� ���������,
    //    �������������� ���������
    //    5 � ������-���������� �����
    //    8 � ������� ������
    private String tradingSessionSubID; // 625 ������ ������
    // ���� ��� REPO
    private String stopPx; // 99
    private Date startDate; // 916
    private Date endDate; // 917
    private String endCash; // 922
    private List<Underlying> underlyings;

    // ������������� 24.04.2015
    private String accruedInterestrate; // 158
    private Integer priceType; // 423 ????

    // ��������� ��� REPO
    public final static int STOP_PX = 99; // ���� ��������
    public final static int START_DATE = 916; //  ���� �������� ����
    public final static int END_DATE = 917; //  ���� �������� ����
    public final static int END_CASH = 922; // ����� ��������
    public final static int NO_UNDERLYINGS = 711; // ���������� ��������� � ������
    public final static int UNDERLYING_SYMBOL = 311; // c����� ���������� �����������
    public final static int UNDERLYING_QTY  = 879; // ���������� ���������� �����������
    
    // ������������� 24.04.2015
    public final static int ACCRUED_INTERESTRATE = 158;
    public final static int PRICE_TYPE = 423;

    public KaseExecutionReport() {}

    /**
     * @return the account
     */
    @XmlElement(name="Account")
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the avgPx
     */
    @XmlElement(name="AvgPx")
    public String getAvgPx() {
        return avgPx;
    }

    /**
     * @param avgPx the avgPx to set
     */
    public void setAvgPx(String avgPx) {
        this.avgPx = avgPx;
    }

    /**
     * @return the clOrdID
     */
    @XmlElement(name="ClOrdID")
    public String getClOrdID() {
        return clOrdID;
    }

    /**
     * @param clOrdID the clOrdID to set
     */
    public void setClOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    /**
     * @return the cumQty
     */
    @XmlElement(name="CumQty")
    public Long getCumQty() {
        return cumQty;
    }

    /**
     * @param cumQty the cumQty to set
     */
    public void setCumQty(Long cumQty) {
        this.cumQty = cumQty;
    }

    /**
     * @return the execID
     */
    @XmlElement(name="ExecID")
    public String getExecID() {
        return execID;
    }

    /**
     * @param execID the execID to set
     */
    public void setExecID(String execID) {
        this.execID = execID;
    }

    /**
     * @return the orderID
     */
    @XmlElement(name="OrderID")
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the orderQty
     */
    @XmlElement(name="OrderQty")
    public Integer getOrderQty() {
        return orderQty;
    }

    /**
     * @param orderQty the orderQty to set
     */
    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    /**
     * @return the ordStatus
     */
    @XmlElement(name="OrdStatus")
    public Character getOrdStatus() {
        return ordStatus;
    }

    /**
     * @param ordStatus the ordStatus to set
     */
    public void setOrdStatus(char ordStatus) {
        this.ordStatus = ordStatus;
    }

    /**
     * @return the ordType
     */
    @XmlElement(name="OrdType")
    public Character getOrdType() {
        return ordType;
    }

    /**
     * @param ordType the ordType to set
     */
    public void setOrdType(char ordType) {
        this.ordType = ordType;
    }

    /**
     * @return the price
     */
    @XmlElement(name="Price")
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the side
     */
    @XmlElement(name="Side")
    public Character getSide() {
        return side;
    }

    /**
     * @param side the side to set
     */
    public void setSide(char side) {
        this.side = side;
    }

    /**
     * @return the symbol
     */
    @XmlElement(name="Symbol")
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the text
     */
    @XmlElement(name="Text")
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the timeInForce
     */
    @XmlElement(name="TimeInForce")
    public Character getTimeInForce() {
        return timeInForce;
    }

    /**
     * @param timeInForce the timeInForce to set
     */
    public void setTimeInForce(Character timeInForce) {
        this.timeInForce = timeInForce;
    }

    /**
     * @return the transactTime
     */
    @XmlElement(name="TransactTime")
    public Date getTransactTime() {
        return transactTime;
    }

    /**
     * @param transactTime the transactTime to set
     */
    public void setTransactTime(Date transactTime) {
        this.transactTime = transactTime;
    }

    /**
     * @return the execType
     */
    @XmlElement(name="ExecType")
    public Character getExecType() {
        return execType;
    }

    /**
     * @param execType the execType to set
     */
    public void setExecType(char execType) {
        this.execType = execType;
    }

    /**
     * @return the leavesQty
     */
    @XmlElement(name="LeavesQty")
    public Long getLeavesQty() {
        return leavesQty;
    }

    /**
     * @param leavesQty the leavesQty to set
     */
    public void setLeavesQty(Long leavesQty) {
        this.leavesQty = leavesQty;
    }

    /**
     * @return the cashOrderQty
     */
    @XmlElement(name="CashOrderQty")
    public String getCashOrderQty() {
        return cashOrderQty;
    }

    /**
     * @param cashOrderQty the cashOrderQty to set
     */
    public void setCashOrderQty(String cashOrderQty) {
        this.cashOrderQty = cashOrderQty;
    }

    /**
     * @return the tradingSessionID
     */
    @XmlElement(name="TradingSessionID")
    public String getTradingSessionID() {
        return tradingSessionID;
    }

    /**
     * @param tradingSessionID the tradingSessionID to set
     */
    public void setTradingSessionID(String tradingSessionID) {
        this.tradingSessionID = tradingSessionID;
    }

    /**
     * @return the lastPx
     */
    public String getLastPx() {
        return lastPx;
    }

    /**
     * @param lastPx the lastPx to set
     */
    public void setLastPx(String lastPx) {
        this.lastPx = lastPx;
    }

    /**
     * @return the lastQty
     */
    public Integer getLastQty() {
        return lastQty;
    }

    /**
     * @param lastQty the lastQty to set
     */
    public void setLastQty(Integer lastQty) {
        this.lastQty = lastQty;
    }

    /**
     * @return the effectiveTime
     */
    public Date getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * @param effectiveTime the effectiveTime to set
     */
    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * @return the yield
     */
    public String getYield() {
        return yield;
    }

    /**
     * @param yield the yield to set
     */
    public void setYield(String yield) {
        this.yield = yield;
    }

    /**
     * @return the orderRestrictions
     */
    public String getOrderRestrictions() {
        return orderRestrictions;
    }

    /**
     * @param orderRestrictions the orderRestrictions to set
     */
    public void setOrderRestrictions(String orderRestrictions) {
        this.orderRestrictions = orderRestrictions;
    }

    /**
     * @return the tradingSessionSubID
     */
    public String getTradingSessionSubID() {
        return tradingSessionSubID;
    }

    /**
     * @param tradingSessionSubID the tradingSessionSubID to set
     */
    public void setTradingSessionSubID(String tradingSessionSubID) {
        this.tradingSessionSubID = tradingSessionSubID;
    }

    /**
     * @return the type
     */
    @XmlElement(name="DealType")
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof KaseExecutionReport) {
            KaseExecutionReport ker = (KaseExecutionReport)obj;
            return (ker.orderID.equals(orderID)) && (ker.execID.equals(execID));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += orderID.hashCode();
        result += execID.hashCode();
        return result;
    }
    
    @XmlElement(name="StopPx")
    public String getStopPx() {
        return stopPx;
    }

    public void setStopPx(String stopPx) {
        this.stopPx = stopPx;
    }

    @XmlElement(name="StartDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlElement(name="EndCash")
    public String getEndCash() {
        return endCash;
    }

    public void setEndCash(String endCash) {
        this.endCash = endCash;
    }

    @XmlElementWrapper(name="Underlyings")
    @XmlElement(name="Underlying")
    public List<Underlying> getUnderlyings() {
        return underlyings;
    }

    public void setUnderlyings(List<Underlying> underlyings) {
        this.underlyings = underlyings;
    }

    @XmlElement(name="EndDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @XmlElement(name="AccruedInterestrate")
    public String getAccruedInterestrate() {
        return accruedInterestrate;
    }

    public void setAccruedInterestrate(String accruedInterestrate) {
        this.accruedInterestrate = accruedInterestrate;
    }

    @XmlElement(name="PriceType")
    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }
}