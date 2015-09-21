package ru.bis.integration.kase.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shds
 *
 */
public class Context {
    private Message message;

    private Map<String, Object> props;

    public static final String REQUEST_PROPERTY = "REQUEST";
    public static final String TRANSPORT_PROPERTY = "SOCKET"; 

    public Context() {
        props = new HashMap<String, Object>();
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void addProperty(String name, Object value) {
        props.put(name, value);
    }

    public Object getProperty(String name) {
        return props.get(name);
    }
}