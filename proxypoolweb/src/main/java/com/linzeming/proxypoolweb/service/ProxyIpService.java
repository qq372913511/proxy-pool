package com.linzeming.proxypoolweb.service;


import com.linzeming.proxypoolweb.dao.ProxyIpDao;
import com.linzeming.proxypoolweb.model.ProxyIp;
import com.linzeming.proxypoolweb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProxyIpService {
    @Autowired
    ProxyIpDao proxyIpDao;

    public List<ProxyIp> getAvailableProxy(Integer count) {
        if (count <= 0) {
            return null;
        }
        List<ProxyIp> allProxyByScoreRange = proxyIpDao.getAllProxyByScoreRange(Constants.validatedProxyMaxScore, Constants.validatedProxyMaxScore);
        if (allProxyByScoreRange == null) {
            return null;
        }
        if (count >= allProxyByScoreRange.size()) {
            return allProxyByScoreRange;
        } else {
            ArrayList<ProxyIp> rtnList = new ArrayList<>();
            Iterator<ProxyIp> iterator = allProxyByScoreRange.iterator();
            for (int i = 0; i < count; i++) {
                if (iterator.hasNext()) {
                    rtnList.add(iterator.next());
                }
            }
            return rtnList;
        }

    }
}
