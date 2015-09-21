package ru.bis.integration.kase.domain.next;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author shds
 *
 * ������ 711 � ExecutionReport
 * ������ ��������� ������������ ��� ����
 */
@XmlRootElement
public class Underlying {
    private String underlyingSymbol; // 311 ������ ���������� �����������
    private String underlyingQty; // 879 ���������� ���������� �����������

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