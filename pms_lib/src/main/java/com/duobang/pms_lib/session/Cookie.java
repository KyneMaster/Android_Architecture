package com.duobang.pms_lib.session;

public class Cookie {

    private String sessionId;
    private String domain;
    private String path;
    private long max_age;
    private boolean httpOnly;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getMax_age() {
        return max_age;
    }

    public void setMax_age(long max_age) {
        this.max_age = max_age;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
}
