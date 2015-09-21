package ru.bis.integration.kase.domain;

/**
 * @author shds
 *
 */
public enum RequestType {

    GET_EXECUTION_REPORTS("EXECUTION_REPORTS"), GET_MARKET_DATA("MARKET_DATA"), UNKNOWN_REQUEST("UNKNOWN_REQUEST");

    private String name;

    RequestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "RequestType[name=" + name + "]";
    }
}