package ru.bis.integration.kase.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import kz.kase.fix.FixProtocol;
import kz.kase.fix.messages.ExecutionReport;
import kz.kase.fix.messages.MDIncRefresh;

import org.apache.log4j.Logger;

import quickfix.Group;
import ru.bis.integration.kase.domain.next.KaseExecutionReport;
import ru.bis.integration.kase.domain.next.MarketDataIncrementalRefresh;
import ru.bis.integration.kase.domain.next.MarketDataSubMessage;
import ru.bis.integration.kase.domain.next.Underlying;

/**
 * @author shds
 *
 */
public class KaseMessageConverter {

    private static Logger logger = Logger.getLogger(KaseMessageConverter.class);
    private static DecimalFormat df;

    static {
        df = new DecimalFormat("#");
        df.setMinimumIntegerDigits(1);
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(6);
    }

    public static KaseExecutionReport convert(ExecutionReport msg) {
        KaseExecutionReport er = new KaseExecutionReport();
        try {
            if (msg.isSetField(FixProtocol.FIELD_ACCOUNT)) {
                er.setAccount(msg.getAccount());
            }
            if (msg.isSetField(FixProtocol.FIELD_CUM_QTY)) {
                er.setCumQty(msg.getCumQty());
            }
            if (msg.isSetField(FixProtocol.FIELD_EXEC_ID)) {
                er.setExecID(msg.getExecId());
            }
            if (msg.isSetField(FixProtocol.FIELD_ORDER_ID)) {
                er.setOrderID(msg.getOrderId());
            }
            if (msg.getOrderStatus()!=null) {
                er.setOrdStatus(msg.getOrderStatus().getValue());
            }
            if (msg.getOrderType()!=null) {
                er.setOrdType(msg.getOrderType().getValue());
            }
            if (msg.isSetField(FixProtocol.FIELD_PRICE)) {
                er.setPrice(convertDoubleToString(msg.getPrice()));
            }
            if (msg.getSide()!=null) {
                er.setSide(msg.getSide().getValue());
            }
            if (msg.isSetField(FixProtocol.FIELD_SYMBOL)) {
                er.setSymbol(msg.getString(FixProtocol.FIELD_SYMBOL));
            }
            if (msg.isSetField(FixProtocol.FIELD_TEXT)) {
                er.setText(msg.getString(FixProtocol.FIELD_TEXT));
            }
            if (msg.getExecType()!=null) {
                er.setExecType(msg.getExecType().getValue());
            }
            if (msg.isSetField(FixProtocol.FIELD_ORDER_QTY)) {
                er.setOrderQty(msg.getInt(FixProtocol.FIELD_ORDER_QTY));
            }
            if (msg.isSetField(FixProtocol.FIELD_LAST_QTY)) {
                er.setLastQty(msg.getInt(FixProtocol.FIELD_LAST_QTY));
            }
            if (msg.isSetField(FixProtocol.FIELD_TIME_IN_FORCE)) {
                er.setTimeInForce(msg.getTimeInForce());
            }
            if (msg.isSetField(FixProtocol.FIELD_TRANSACTION_TIME)) {
                er.setTransactTime(msg.getTransactionTime());
            }
            if (msg.isSetField(FixProtocol.FIELD_LEAVES_QTY)) {
                er.setLeavesQty(msg.getLeavesQty());
            }
            if (msg.isSetField(FixProtocol.FIELD_LAST_QTY)) {
                er.setLastQty(msg.getLastQty());
            }
            if (msg.isSetField(FixProtocol.FIELD_LAST_QTY)) {
                er.setLastQty(msg.getLastQty());
            }
            if (msg.isSetField(FixProtocol.FIELD_EFFECTIVE_TIME)) {
                er.setEffectiveTime(msg.getEffectiveTime());
            }
            if (msg.isSetField(FixProtocol.FILED_YIELD)) {
                er.setYield(convertDoubleToString(msg.getYield()));
            }
            if (msg.isSetField(FixProtocol.FIELD_ORDER_RESTRICTIONS)) {
                er.setOrderRestrictions(msg.getOrderRestrictions());
            }
            if (msg.isSetField(FixProtocol.FIELD_DEAL_TYPE)) {
                er.setType(msg.getString(FixProtocol.FIELD_DEAL_TYPE));
            }
            if (msg.isSetField(KaseExecutionReport.STOP_PX)) {
                er.setStopPx(convertDoubleToString(msg.getDouble(KaseExecutionReport.STOP_PX)));
            }
            if (msg.isSetField(KaseExecutionReport.START_DATE)) {
                er.setStartDate(msg.getUtcDateOnly(KaseExecutionReport.START_DATE));
            }
            if (msg.isSetField(KaseExecutionReport.END_CASH)) {
                er.setEndCash(convertDoubleToString(msg.getDouble(KaseExecutionReport.END_CASH)));
            }
            if (msg.isSetField(KaseExecutionReport.END_DATE)) {
                er.setEndDate(msg.getUtcDateOnly(KaseExecutionReport.END_DATE));
            }
            if (msg.isSetField(KaseExecutionReport.ACCRUED_INTERESTRATE)) {
                er.setAccruedInterestrate(msg.getString(KaseExecutionReport.ACCRUED_INTERESTRATE));
            }
            if (msg.isSetField(KaseExecutionReport.PRICE_TYPE)) {
                er.setPriceType(msg.getInt(KaseExecutionReport.PRICE_TYPE));
            }
            List<Group> groups = msg.getGroups(KaseExecutionReport.NO_UNDERLYINGS);
            if (groups != null) {
                List<Underlying> underlyings = new ArrayList<>();
                for (Group g : groups) {
                    Underlying u = new Underlying();
                    if (g.isSetField(KaseExecutionReport.UNDERLYING_SYMBOL)) {
                        u.setUnderlyingSymbol(g.getString(KaseExecutionReport.UNDERLYING_SYMBOL));
                    }
                    if (g.isSetField(KaseExecutionReport.UNDERLYING_QTY)) {
                        u.setUnderlyingQty(convertDoubleToString(g.getDouble(KaseExecutionReport.UNDERLYING_QTY)));
                    }
                    underlyings.add(u);
                }
                er.setUnderlyings(underlyings);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return er;
    }

    public static MarketDataIncrementalRefresh convert(MDIncRefresh msg) {
        List<MarketDataSubMessage> marketDataSubMessageList = new ArrayList<>();
        List<Group> groups = msg.getGroups(FixProtocol.FIELD_NO_MD_ENTRIES);
        if (groups != null) {
            for (Group g: groups) {
                if (!g.isSetField(FixProtocol.FIELD_SYMBOL)) continue;
                MarketDataSubMessage md = new MarketDataSubMessage();
                md.setSymbol(g.getString(FixProtocol.FIELD_SYMBOL));
                if (g.isSetField(FixProtocol.FIELD_UPDATE_ACTION)) {
                    md.setMdUpdateAction(g.getChar(FixProtocol.FIELD_UPDATE_ACTION));
                }
                if (g.isSetField(FixProtocol.FIELD_MD_ENTRY_PRICE)) {
                    md.setMdEntryPrice(convertDoubleToString(g.getDouble(FixProtocol.FIELD_MD_ENTRY_PRICE)));
                }
                if (g.isSetField(FixProtocol.FIELD_MD_ENTRY_SIZE)) {
                    md.setMDEntrySize(convertDoubleToString(g.getDouble(FixProtocol.FIELD_MD_ENTRY_SIZE)));
                }
                if (g.isSetField(FixProtocol.FIELD_MD_ENTRY_TYPE)) {
                    md.setMdEntryType(g.getChar(FixProtocol.FIELD_MD_ENTRY_TYPE));
                }
                if (g.isSetField(FixProtocol.FIELD_LAST_QTY)) {
                    md.setLastQty(g.getLong(FixProtocol.FIELD_LAST_QTY));
                }
                if (g.isSetField(FixProtocol.FIELD_LAST_DEAL_BEFORE_TODAY_PRICE)) {
                    md.setLastDealBeforeTodayPrice(convertDoubleToString(g.getDouble(FixProtocol.FIELD_LAST_DEAL_BEFORE_TODAY_PRICE)));
                }
                if (g.isSetField(FixProtocol.FIELD_LAST_DEAL_BEFORE_TODAY_VOLUME)) {
                    md.setLastDealBeforeTodayVolume(convertDoubleToString(g.getDouble(FixProtocol.FIELD_LAST_DEAL_BEFORE_TODAY_VOLUME)));
                }
                if (g.isSetField(FixProtocol.FIELD_LAST_DEAL_BEFORE_TODAY_TIME)) {
                    md.setLastDealBeforeTodayTime(g.getUtcDateOnly(FixProtocol.FIELD_LAST_DEAL_BEFORE_TODAY_TIME));
                }
                if (g.isSetField(FixProtocol.FIELD_LAST_DAY_AVG_PRICE)) {
                    md.setLastDayAvgPrice(convertDoubleToString(g.getDouble(FixProtocol.FIELD_LAST_DAY_AVG_PRICE)));
                }
                // Trade Session section
                if (g.isSetField(FixProtocol.FIELD_TRADE_SESSION_ID)) {
                    md.setTradeSessionId(g.getInt(FixProtocol.FIELD_TRADE_SESSION_ID));
                }
                if (g.isSetField(FixProtocol.FIELD_NUMBER_OF_ORDERS)) {
                    md.setNumberOfOrders(g.getInt(FixProtocol.FIELD_NUMBER_OF_ORDERS));
                }
                if (g.isSetField(FixProtocol.FIELD_PRICE_DELTA)) {
                    md.setPriceDelta(convertDoubleToString(g.getDouble(FixProtocol.FIELD_PRICE_DELTA)));
                }
                if (g.isSetField(FixProtocol.FIELD_TRADE_VOLUME)) {
                    md.setTradeVolume(g.getString(FixProtocol.FIELD_TRADE_VOLUME));
                }
                if (g.isSetField(FixProtocol.FIELD_DEALS_COUNT)) {
                    md.setDealsCount(g.getInt(FixProtocol.FIELD_DEALS_COUNT));
                }
                if (g.isSetField(FixProtocol.FIELD_DEALS_VOLUME)) {
                    md.setDealsVolume(convertDoubleToString(g.getDouble(FixProtocol.FIELD_DEALS_VOLUME)));
                }
                if (g.isSetField(FixProtocol.FIELD_DEALS_QTY_TOTAL)) {
                    md.setDealsQtyTotal((long) g.getDouble(FixProtocol.FIELD_DEALS_QTY_TOTAL));
                }
                if (g.isSetField(FixProtocol.FIELD_AVERAGE_WEIGHTED_PRICE)) {
                    md.setAverageWeightedPrice(convertDoubleToString(g.getDouble(FixProtocol.FIELD_AVERAGE_WEIGHTED_PRICE)));
                }
                if (g.isSetField(MarketDataIncrementalRefresh.LAST_DAY_BEFORE_AVG_WEIGHTED_PRICE)) {
                    md.setLastDayBeforeAvgWeightedPrice(convertDoubleToString(g.getDouble(MarketDataIncrementalRefresh.LAST_DAY_BEFORE_AVG_WEIGHTED_PRICE)));
                }
                marketDataSubMessageList.add(md);
            }
        }
        MarketDataIncrementalRefresh mdir = new MarketDataIncrementalRefresh();
        mdir.setMarketDataSubMessageList(marketDataSubMessageList);
        return mdir;
    }

    private static String convertDoubleToString(double d) {
        return df.format(d).replace(",", ".");
    }
}