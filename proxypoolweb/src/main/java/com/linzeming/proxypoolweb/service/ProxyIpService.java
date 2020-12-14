package com.linzeming.proxypoolweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linzeming.proxypoolweb.model.ProxyIp;
import com.linzeming.proxypoolweb.model.ProxyIpValidateLogResult;

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
    List<ProxyIp> getLatestVerifiedProxyIpByPage(Page<ProxyIp> page);

    List<ProxyIpValidateLogResult> selectProxyIpValidateResultByIpAndPort(String ip, Integer port);

}
