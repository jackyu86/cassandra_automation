package com.jd.jg.admin.controller;

import com.alibaba.fastjson.JSON;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.jd.jg.admin.cass.CassandraClient;
import com.jd.jg.admin.model.DiTraceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TraceLogController {

    @Autowired
    CassandraClient cass;

    @RequestMapping(value = "/logs",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String jobs(@RequestParam("app")String app,@RequestParam("id")String id){
        String cql = "select app,id,type,toTimestamp(ts),cql,message from debug.di_trace_log where app=? and id=?";

        List<DiTraceLog> logs = new ArrayList<>();

        ResultSet rs = cass.getSession().execute(cql, app, id);
        for(Row r: rs.all()){
            DiTraceLog l = new DiTraceLog();
            int i = 0;
            l.app = r.getString(i++);
            l.id = r.getString(i++);
            l.type = r.getString(i++);
            l.ts = r.getTimestamp(i++);
            l.cql = r.getString(i++);
            l.message = r.getString(i++);
            if( l.message == null)l.message= " ";
            logs.add(l);
        }

        return JSON.toJSONString(logs);
    }

    @RequestMapping(value = "/trace",method = RequestMethod.GET)
    public String trace(){
        return "trace";
    }
}
