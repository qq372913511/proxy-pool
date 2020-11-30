package com.linzeming.proxypool.crawler.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class ProxyIp {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String ip;
    private Integer port;
    private String country;
    private String city;
    private Integer isHttps;
    private Integer anonymity;
    private Integer connectionSpeed;
    private Date gmtLastValidate;
    private Date gmtCreate;
    private Date gmtModified;


    public ProxyIp() {
    }

    public ProxyIp(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return "ProxyIp{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", isHttps=" + isHttps +
                ", anonymity=" + anonymity +
                ", connectionSpeed=" + connectionSpeed +
                ", gmtLastValidate=" + gmtLastValidate +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getIsHttps() {
        return isHttps;
    }

    public void setIsHttps(Integer isHttps) {
        this.isHttps = isHttps;
    }

    public Integer getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(Integer anonymity) {
        this.anonymity = anonymity;
    }

    public Integer getConnectionSpeed() {
        return connectionSpeed;
    }

    public void setConnectionSpeed(Integer connectionSpeed) {
        this.connectionSpeed = connectionSpeed;
    }

    public Date getGmtLastValidate() {
        return gmtLastValidate;
    }

    public void setGmtLastValidate(Date gmtLastValidate) {
        this.gmtLastValidate = gmtLastValidate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String formattedIpPort() {
        return ip + ":" + port;
    }
}
