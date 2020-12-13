package com.linzeming.proxypoolweb.model;

public class ProxyIpValidateLogResult {
    String ipPort;
    String localDateTime;
    String connctionSpeed;

    public ProxyIpValidateLogResult() {
    }

    public ProxyIpValidateLogResult(String ipPort) {
        this.ipPort = ipPort;
    }

    public ProxyIpValidateLogResult(String ipPort, String localDateTime, String connctionSpeed) {
        this.ipPort = ipPort;
        this.localDateTime = localDateTime;
        this.connctionSpeed = connctionSpeed;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getConnctionSpeed() {
        return connctionSpeed;
    }

    public void setConnctionSpeed(String connctionSpeed) {
        this.connctionSpeed = connctionSpeed;
    }

    @Override
    public String toString() {
        return "ProxyIpValidateLogResult{" +
                "ipPort='" + ipPort + '\'' +
                ", localDateTime='" + localDateTime + '\'' +
                ", connctionSpeed='" + connctionSpeed + '\'' +
                '}';
    }
}
