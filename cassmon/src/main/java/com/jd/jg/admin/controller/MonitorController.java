package com.jd.jg.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jd.jg.admin.cass.CassLogAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MonitorController {

    @Autowired
    CassLogAnalysis cla;

    @RequestMapping(value = "/jobs",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String jobs(ModelMap model){
        return JSON.toJSONString(cla.analysis(), SerializerFeature. WriteMapNullValue);
    }

    @RequestMapping(value = "/job",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

}