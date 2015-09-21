package ru.bis.integration.kase.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.bis.integration.kase.domain.next.KaseExecutionReport;
import ru.bis.integration.kase.domain.next.MarketDataIncrementalRefresh;

/**
 * @author shds
 *
 */
public final class KaseRepository {
    private final Set<KaseExecutionReport> reports;
    private final List<MarketDataIncrementalRefresh> marketData;
    private final Set<String> symbols;

    public KaseRepository() {
        reports = new HashSet<>();
        marketData = new ArrayList<>();
        symbols = new HashSet<>();
    }

    public void addExecutionReport(KaseExecutionReport er) {
        reports.add(er);
    }

    public List<KaseExecutionReport> getExecutionReports() {
        return Collections.unmodifiableList(new ArrayList<>(reports));
    }

    public void addSymbol(String symbol) {
        symbols.add(symbol);
    }

    public Set<String> getSymbols() {
        return Collections.unmodifiableSet(symbols);
    }

    public void addMarketDataIncrementalRefresh(MarketDataIncrementalRefresh mdir) {
        marketData.add(mdir);
    }

    public List<MarketDataIncrementalRefresh> getMarketData() {
        return Collections.unmodifiableList(marketData);
    }
}