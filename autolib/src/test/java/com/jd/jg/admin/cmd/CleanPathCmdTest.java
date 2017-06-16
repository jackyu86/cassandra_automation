package com.jd.jg.admin.cmd;

import org.junit.Test;


public class CleanPathCmdTest {

    @Test
    public void testExecute(){
        CleanPathCmd cmd = new CleanPathCmd();
        cmd.path = "/export/cassandra/test/";
        cmd.execute("192.168.200.56");
    }

}