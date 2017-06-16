package com.jd.jg.admin.controller;

import com.jd.jg.admin.cass.CassandraClient;
import org.junit.Test;


public class QueryControllerTest {

    @Test
    public void testQuery(){
        CassandraClient cass = new CassandraClient();
        cass.init("192.168.200.67",200);
    }
}