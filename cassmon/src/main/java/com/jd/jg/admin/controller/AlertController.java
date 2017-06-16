package com.jd.jg.admin.controller;

import com.alibaba.fastjson.JSON;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Strings;
import com.jd.jg.admin.cass.CassandraClient;
import com.jd.jg.admin.model.AlertInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class AlertController {

    Logger logger = LoggerFactory.getLogger(AlertController.class);

    long lastAlertReceived = -1l;

    public static final String ACT_618 = "jinggang.di.cass.act618";

    List<UserAlert> buffer = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    CassandraClient cass;

    @RequestMapping(value = "/alert",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String sendAlert(@RequestBody String alertStr){
        UserAlert alert = null;
        try {
            alert = JSON.parseObject(alertStr, UserAlert.class);
        }catch (Exception ex){
            logger.error("failed to parse json {}",alertStr,ex);
            return "{'code':-1}";
        }

        if( ACT_618.equals(alert.key)){
            buffer.add(alert);
            logger.info(alert.message);
        }
        else {
            Profiler.businessAlarm(alert.key, alert.message);
        }

        if( ! Strings.isNullOrEmpty(alert.message)) {
            alert.message = alert.message.replaceAll("\"", "");
        }

        try {
            Insert insert = QueryBuilder.insertInto("ur", "user_alert").value("key", alert.key).value("ts", new Date()).value("debug_info", alert.debugInfo).value("message", alert.message);
            cass.getSession().execute(insert);
        }catch (Exception ex) {
            logger.warn("failed to save alert to cass: {}",alertStr,ex);
        }

        return "{'code':1}";
    }

    public void sendAggAlert(){
        if( buffer.size() == 0)return;

        List<UserAlert> oldBuf = buffer;
        buffer = Collections.synchronizedList(new ArrayList<>());

        StringBuilder sb = new StringBuilder();
        sb.append("total ").append(oldBuf.size()).append(".");
        for (UserAlert a : oldBuf) {
            sb.append(a.message).append(",");
        }

        Profiler.businessAlarm(ACT_618, sb.toString());
        logger.info("send {} message to ump",oldBuf.size());

    }

}

class UserAlert{
    public String key;
    public String debugInfo;
    public String message;
}