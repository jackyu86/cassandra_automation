package com.jd.jg.admin.controller;

import com.alibaba.fastjson.JSON;
import com.jd.jg.admin.cass.CassandraClient;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertControllerTest {
    @Test
    public void sendAlert() throws Exception {
        UserAlert ua = new UserAlert();
        ua.key = "jg.cass.user";
        ua.debugInfo = "{'test':'ddd'}";
        ua.message = "测试";

        String jsonStr = JSON.toJSONString(ua);
        System.out.println(jsonStr);

        CassandraClient cass = new CassandraClient();
        cass.init("192.168.200.67",200);

        AlertController ac = new AlertController();
        ac.cass = cass;
        ac.sendAlert(jsonStr);
    }

}