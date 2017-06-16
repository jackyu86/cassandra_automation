package com.jd.jg.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.datastax.driver.core.*;
import com.jd.jg.admin.cass.CassandraClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QueryController {

    Map<String,CassandraClient> clusters = new HashMap<>();

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(){
        return "query";
    }

    @RequestMapping(value = "/q",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String query(@RequestParam("query")String query,@RequestParam("cluster")String cluster){
        CassandraClient cass = clusters.get(cluster);
        if( cass == null){
            cass = new CassandraClient();
            cass.init(cluster,200);
            clusters.put(cluster,cass);
        }

        Statement st = new SimpleStatement(query);
        st.setConsistencyLevel(ConsistencyLevel.ONE);

        long start = System.currentTimeMillis();
        ResultSet rs = cass.getSession().execute(st);
        int ts = (int)(System.currentTimeMillis() - start);
        List<String> cols = getColumns(rs);

        if( rs == null)return "{}";

        List<JSONObject> res = new ArrayList<>();
        for( Row r : rs.all()){
            JSONObject jo = new JSONObject();
            for( String c : cols) {
                Object v = r.getObject(c);
                if( v == null) v = "";
                jo.put(c, JSON.toJSONString(v));
            }
            res.add(jo);
        }

        JSONObject r = new JSONObject();
        r.put("time",ts);
        r.put("rows",res);
        return JSON.toJSONString(r, SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.PrettyFormat);
    }

    public List<String> getColumns(ResultSet rs){
        List<String> cols = new ArrayList<>();

        ColumnDefinitions coldef = rs.getColumnDefinitions();
        for( ColumnDefinitions.Definition d : coldef.asList()){
            cols.add(d.getName());
        }

        return cols;
    }

}
