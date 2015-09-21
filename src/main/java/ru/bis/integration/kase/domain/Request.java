package ru.bis.integration.kase.domain;

import java.util.Date;

/**
 * @author shds
 *
 */
public class Request {
    private RequestType type;
    private Date created;

    public Request(RequestType type) {
        this.type = type;
        created = new Date();
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public RequestType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Request [type=" + type + ", created=" + created + "]";
    }
}