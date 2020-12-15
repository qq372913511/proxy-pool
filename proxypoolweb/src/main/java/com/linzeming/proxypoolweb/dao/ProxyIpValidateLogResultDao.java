package com.linzeming.proxypoolweb.dao;

import com.linzeming.proxypoolweb.model.ProxyIpValidateLogResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProxyIpValidateLogResultDao extends MongoRepository<ProxyIpValidateLogResult, String> {
    public List<ProxyIpValidateLogResult> findByProxyIpIdAndGmtLastValidateGreaterThanOrderByGmtLastValidateAsc(Integer proxyIpId, LocalDateTime dateGreaterThan);
}
