package com.linzeming.proxypool.crawler.mapper;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.linzeming.proxypool.crawler.model.ProxyIp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linzeming
 * @since 2020-11-30
 */
public interface ProxyIpMapper extends BaseMapper<ProxyIp> {
    int insertNewProxyIpIgnoreIntoBatch(List<ProxyIp> proxyIpList);
    int insertNewProxyIpIgnoreInto(ProxyIp proxyIp);
}
