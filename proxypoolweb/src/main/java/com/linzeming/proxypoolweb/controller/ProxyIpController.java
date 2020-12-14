package com.linzeming.proxypoolweb.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linzeming
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/proxyIp")
public class ProxyIpController {
    @GetMapping("get")
    public void getTest(){
        System.out.println("getTestSuccess");
    }
}

