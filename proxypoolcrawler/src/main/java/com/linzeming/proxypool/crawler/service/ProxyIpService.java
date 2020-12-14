package com.linzeming.proxypool.crawler.service;

import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linzeming
 * @since 2020-11-30
 */
public interface ProxyIpService extends IService<ProxyIp> {
    int insertProxyIpIgnoreIntoBatch(List<ProxyIp> proxyIpList);
    int insertProxyIpIgnoreInto(ProxyIp proxyIp);

}
