package com.linzeming.proxypoolweb.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linzeming.proxypoolweb.dao.ProxyIpRedisDao;
import com.linzeming.proxypoolweb.mapper.ProxyIpMapper;
import com.linzeming.proxypoolweb.model.ProxyIp;
import com.linzeming.proxypoolweb.model.ProxyIpValidateLogResult;
import com.linzeming.proxypoolweb.service.ProxyIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author linzeming
 * @since 2020-11-30
 */
@Service
@Transactional
public class ProxyIpServiceImpl extends ServiceImpl<ProxyIpMapper, ProxyIp> implements ProxyIpService {
    @Autowired
    ProxyIpMapper proxyIpMapper;
    @Autowired
    ProxyIpRedisDao proxyIpRedisDao;

    @Override
    public List<ProxyIp> getLatestVerifiedProxyIpByPage(Page<ProxyIp> page) {
        if (page == null) return null;
        QueryWrapper<ProxyIp> proxyIpQueryWrapper = new QueryWrapper<>();
        proxyIpQueryWrapper.select("id","ip", "port", "country", "city", "is_https", "anonymity", "connection_speed",
                "gmt_last_validate", "gmt_create");
        proxyIpQueryWrapper.gt("connection_speed",0);
        proxyIpQueryWrapper.orderByDesc("gmt_last_validate");
        Page<ProxyIp> proxyIps = proxyIpMapper.selectPage(page, proxyIpQueryWrapper);
        List<ProxyIp> records = proxyIps.getRecords();
        return records;
    }

    @Override
    public List<ProxyIpValidateLogResult> selectProxyIpValidateResultByIpAndPort(String ip, Integer port) {
        return proxyIpRedisDao.selectProxyIpValidateResultByIpAndPort(ip, port);
    }
}
