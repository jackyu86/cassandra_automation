package com.jd.jg.admin.cmd;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckProcessCmdTest {

    @Test
    public void testExecute(){
        CheckProcessCmd cmd = new CheckProcessCmd();
        cmd.processName = "cassandra";
        cmd.expect = "java";

        cmd.execute("192.168.200.56");
    }

}