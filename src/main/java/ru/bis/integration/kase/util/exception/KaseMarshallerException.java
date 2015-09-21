package ru.bis.integration.kase.util.exception;

/**
 * @author shds
 *
 */
public class KaseMarshallerException extends Exception {

    private static final long serialVersionUID = 8613975809422812620L;

    public KaseMarshallerException(Exception ex) {
        super (ex);
    }
}
