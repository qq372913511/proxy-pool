package com.linzeming.proxypoolweb.controller;


import com.linzeming.proxypoolweb.model.ProxyIpValidateLogResult;
import com.linzeming.proxypoolweb.service.ProxyIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IpHistoryController {
    @Autowired
    ProxyIpService proxyIpService;


    @GetMapping("/ipHistory")
    public String ipHistory(Model model,
                            @RequestParam(value = "ip", required = true) String ip,
                            @RequestParam(value = "port", required = true) String port) {
        List<ProxyIpValidateLogResult> results = proxyIpService.selectProxyIpValidateResultByIpAndPort(ip, Integer.valueOf(port));
        model.addAttribute("ipPort", ip + ":" + port);
        model.addAttribute("datas", results);

        //组装echart x轴数据和y轴数据
        ArrayList<String> xaxisDatas = new ArrayList<>(results.size());
        ArrayList<Long> yaxisDatas = new ArrayList<>(results.size());
        for (ProxyIpValidateLogResult result:
             results) {
            xaxisDatas.add(result.getLocalDateTime());
            yaxisDatas.add(Long.valueOf(result.getConnctionSpeed()));
        }
        model.addAttribute("xaxisDatas", xaxisDatas);
        model.addAttribute("yaxisDatas", yaxisDatas);
        return "ipHistory";
    }


}
