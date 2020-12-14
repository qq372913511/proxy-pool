package com.linzeming.proxypool.crawler.service.impl;

import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.linzeming.proxypool.crawler.mapper.ProxyIpMapper;
import com.linzeming.proxypool.crawler.service.ProxyIpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
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


    @Override
    public int insertProxyIpIgnoreIntoBatch(List<ProxyIp> proxyIpList) {
        return proxyIpMapper.insertNewProxyIpIgnoreIntoBatch(proxyIpList);
    }

    @Override
    public int insertProxyIpIgnoreInto(ProxyIp proxyIp) {
        return proxyIpMapper.insertNewProxyIpIgnoreInto(proxyIp);
    }
}
