package com.linzeming.proxypool.crawler.entity;

public class ProxyIp {
    private String ip;
    private Integer port;
    private Double score;


    public ProxyIp() {
    }

    public ProxyIp(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.score = 0.0;
    }

    public ProxyIp(String ip, Integer port, Double score) {
        this.ip = ip;
        this.port = port;
        this.score = score;
    }

    public ProxyIp(String ip, String port) {
        this.ip = ip;
        this.port = Integer.valueOf(port);
        this.score = 0.0;
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

    @Override
    public String toString() {
        return "Proxy{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", score=" + score +
                '}';
    }

    public String getFormattedIpPort() {
        return ip + ":" + port;
    }
}
