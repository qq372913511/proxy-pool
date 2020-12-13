package com.linzeming.proxypoolweb.dao;

import com.linzeming.proxypoolweb.model.ProxyIpValidateLogResult;

import java.util.List;

public interface ProxyIpRedisDao {
    List<ProxyIpValidateLogResult> selectProxyIpValidateResultByIpAndPort(String ip, Integer port);


}
