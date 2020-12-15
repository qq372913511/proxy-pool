package com.linzeming.proxypool.crawler.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author linzeming
 * @since 2020-11-30
 */
public class ProxyIp implements Serializable {

    private static final long serialVersionUID = 1L;


    private ObjectId _id;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }


    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * ip address
     */
    private String ip;

    /**
     * port
     */
    private Integer port;

    private String country;

    private String city;

    /**
     * 是否支持https
     */
    private Integer isHttps;

    /**
     * 匿名度，0-透明，1-普通匿名，2-高度匿名
     */
    private Integer anonymity;

    /**
     * 0代表超时，单位是ms
     */
    private Integer connectionSpeed;

    /**
     * 上次验证时间
     */
    private LocalDateTime gmtLastValidate;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    public ProxyIp() {
    }

    public ProxyIp(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
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

    public LocalDateTime getGmtLastValidate() {
        return gmtLastValidate;
    }

    public void setGmtLastValidate(LocalDateTime gmtLastValidate) {
        this.gmtLastValidate = gmtLastValidate;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "ProxyIp{" +
                "id=" + id +
                ", ip=" + ip +
                ", port=" + port +
                ", country=" + country +
                ", city=" + city +
                ", isHttps=" + isHttps +
                ", anonymity=" + anonymity +
                ", connectionSpeed=" + connectionSpeed +
                ", gmtLastValidate=" + gmtLastValidate +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                "}";
    }

    public String formatIpPort() {
        return ip + ":" + port;
    }
}
