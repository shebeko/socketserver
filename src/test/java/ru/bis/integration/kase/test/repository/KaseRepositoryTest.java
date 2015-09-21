package ru.bis.integration.kase.test.repository;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.bis.integration.kase.domain.next.KaseExecutionReport;
import ru.bis.integration.kase.repository.KaseRepository;

/**
 * @author shds
 *
 */
public class KaseRepositoryTest {

    private String fixedExecId = "12345";
    private String fixedOrderId = "NF12345";
    private final String account="123556";
    private RandomString rsEid = new RandomString(5);
    private RandomString rsOid = new RandomString(7);

    @Test
    public void addExecutionReport() {
        KaseRepository repo = new KaseRepository();
        repo.addExecutionReport(createExecutionReport(true, true));
        repo.addExecutionReport(createExecutionReport(true, true));
        assertEquals(repo.getExecutionReports().size(), 1);
        repo.addExecutionReport(createExecutionReport(true, false));
        assertEquals(repo.getExecutionReports().size(), 2);
    }

    private KaseExecutionReport createExecutionReport(boolean fixedExecId, boolean fixedOrderId) {
        KaseExecutionReport report = new KaseExecutionReport();
        report.setExecID(fixedExecId ? this.fixedExecId : rsEid.nextString());
        report.setOrderID(fixedOrderId ? this.fixedOrderId : rsOid.nextString());
        report.setAccount(account);
        return report;
    }
}