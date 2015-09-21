package ru.bis.integration.kase.domain.next;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author shds
 *
 * Группа 711 в ExecutionReport
 * Группа залоговых инструментов для РЕПО
 */
@XmlRootElement
public class Underlying {
    private String underlyingSymbol; // 311 символ залогового инструмента
    private String underlyingQty; // 879 количество залогового инструмента

    @XmlElement(name="UnderlyingSymbol")
    public String getUnderlyingSymbol() {
        return underlyingSymbol;
    }

    public void setUnderlyingSymbol(String underlyingSymbol) {
        this.underlyingSymbol = underlyingSymbol;
    }

    @XmlElement(name="UnderlyingQty")
    public String getUnderlyingQty() {
        return underlyingQty;
    }

    public void setUnderlyingQty(String underlyingQty) {
        this.underlyingQty = underlyingQty;
    }
}