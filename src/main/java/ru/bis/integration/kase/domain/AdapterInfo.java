package ru.bis.integration.kase.domain;

/**
 * @author shds
 *
 */
public class AdapterInfo {
    private final Mode mode;
    private String erResponseFile;
    private ServerType serverType;

    public AdapterInfo(Mode mode) {
        this.mode = mode;
    }

    public Mode mode() {
        return mode;
    }

    public String getERResponseFile() {
        return erResponseFile;
    }

    public void setERResponseFile(String erResponseFile) {
        this.erResponseFile = erResponseFile;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public ServerType serverType() {
        return serverType;
    }
}