package com.linzeming.proxypoolweb.model;
import java.time.LocalDateTime;

public class ProxyIpValidateLogResult {
    /**
     * mongodb 里面的 object_id
     */
    String _id;

    /**
     * 关联mysql proxy_ip表中的id
     */
    Integer proxyIpId;
    LocalDateTime gmtLastValidate;
    Integer connectionSpeed;

    public ProxyIpValidateLogResult() {
    }

    public ProxyIpValidateLogResult(Integer proxyIpId, LocalDateTime gmtLastValidate, Integer connectionSpeed) {
        this.proxyIpId = proxyIpId;
        this.gmtLastValidate = gmtLastValidate;
        this.connectionSpeed = connectionSpeed;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getProxyIpId() {
        return proxyIpId;
    }

    public void setProxyIpId(Integer proxyIpId) {
        this.proxyIpId = proxyIpId;
    }

    public LocalDateTime getGmtLastValidate() {
        return gmtLastValidate;
    }

    public void setGmtLastValidate(LocalDateTime gmtLastValidate) {
        this.gmtLastValidate = gmtLastValidate;
    }

    public Integer getConnectionSpeed() {
        return connectionSpeed;
    }

    public void setConnectionSpeed(Integer connectionSpeed) {
        this.connectionSpeed = connectionSpeed;
    }

    @Override
    public String toString() {
        return "ProxyIpValidateLogResult{" +
                "_id='" + _id + '\'' +
                ", proxyIpId=" + proxyIpId +
                ", gmtLastValidate=" + gmtLastValidate +
                ", connectionSpeed=" + connectionSpeed +
                '}';
    }

}
