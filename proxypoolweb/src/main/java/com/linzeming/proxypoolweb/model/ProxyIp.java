package com.linzeming.proxypoolweb.model;

public class ProxyIp {
    private String ip;
    private Integer port;
    private Double score;
    private Long validateTimestamp;
    private boolean lastValidateResult;

    public ProxyIp() {
    }

    public ProxyIp(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.score = 0.0;
        this.validateTimestamp = 0L;
    }

    public ProxyIp(String ip, Integer port, Double score) {
        this.ip = ip;
        this.port = port;
        this.score = score;
        this.validateTimestamp = 0L;
    }

    public ProxyIp(String ip, String port) {
        this.ip = ip;
        this.port = Integer.valueOf(port);
        this.score = 0.0;
        this.validateTimestamp = 0L;
    }

    public ProxyIp(String ip, Integer port, Double score, Long validateTimestamp) {
        this.ip = ip;
        this.port = port;
        this.score = score;
        this.validateTimestamp = validateTimestamp;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isLastValidateResult() {
        return lastValidateResult;
    }

    public void setLastValidateResult(boolean lastValidateResult) {
        this.lastValidateResult = lastValidateResult;
    }

    public Long getValidateTimestamp() {
        return validateTimestamp;
    }

    public void setValidateTimestamp(Long validateTimestamp) {
        this.validateTimestamp = validateTimestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String formattedIpPort() {
        return ip + ":" + port;
    }

    public String formattedIpValidateTimestamp() {
        return ip + ":" + validateTimestamp;
    }

    public String formattedIpValidateTimestampResult() {
        return ip + ":" + validateTimestamp + ":" + lastValidateResult;
    }

    @Override
    public String toString() {
        return "ProxyIp{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", score=" + score +
                ", validateTimestamp=" + validateTimestamp +
                ", lastValidateResult=" + lastValidateResult +
                '}';
    }
}
