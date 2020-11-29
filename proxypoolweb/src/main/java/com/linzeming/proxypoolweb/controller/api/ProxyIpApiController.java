package com.linzeming.proxypoolweb.controller.api;

import com.linzeming.proxypoolweb.model.ProxyIp;
import com.linzeming.proxypoolweb.service.ProxyIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ProxyIpApiController {
    @Autowired
    ProxyIpService proxyIpService;

    @GetMapping("/getAvailableProxy")
    public List<ProxyIp> getAvailable(@RequestParam(defaultValue = "1") Integer count){
        return proxyIpService.getAvailableProxy(count);
    }
}
