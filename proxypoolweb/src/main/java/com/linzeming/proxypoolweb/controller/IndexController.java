package com.linzeming.proxypoolweb.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linzeming.proxypoolweb.model.ProxyIp;
import com.linzeming.proxypoolweb.service.ProxyIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    ProxyIpService proxyIpService;


    @GetMapping(value = {"/", "/index"})
    public String index(Model model, @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum) {
        int pageSize = 15;

        Page<ProxyIp> proxyIpPage = new Page<>();
        proxyIpPage.setSize(pageSize);
        proxyIpPage.setCurrent(Long.parseLong(pageNum));

        List<ProxyIp> latestVerifiedProxyIpByPage = proxyIpService.getLatestVerifiedProxyIpByPage(proxyIpPage);
        model.addAttribute("datas", latestVerifiedProxyIpByPage);
        model.addAttribute("pageInfo", proxyIpPage);


        return "index";
    }

}
