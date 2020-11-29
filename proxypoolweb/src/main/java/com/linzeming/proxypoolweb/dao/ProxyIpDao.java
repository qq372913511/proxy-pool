package com.linzeming.proxypoolweb.dao;


import com.fasterxml.jackson.databind.node.DoubleNode;
import com.linzeming.proxypoolweb.model.ProxyIp;

import java.util.List;
import java.util.Set;

public interface ProxyIpDao {
    List<ProxyIp> getAllProxyByScoreRange(Double scoreMin,Double scoreMax);
    Long countProxyByScoreRange(Double scoreMin,Double scoreMax);
    Long getProxyLastValidateTimestamp(ProxyIp proxyIp);

}
