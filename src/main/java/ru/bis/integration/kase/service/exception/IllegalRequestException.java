package ru.bis.integration.kase.service.exception;

/**
 * @author shds
 *
 */
public class IllegalRequestException extends RuntimeException {

    private static final long serialVersionUID = 7136766477050379400L;

    public IllegalRequestException(String msg) {
        super(msg);
    }

    public IllegalRequestException(Exception e) {
        super(e);
    }
}