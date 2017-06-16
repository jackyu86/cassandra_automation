package com.jd.jg.admin.cmd;

import com.jd.jg.admin.config.CassandraConfig;
import org.junit.Test;

public class ScpCmdTest {

    @Test
    public void execute() throws Exception {
        CassandraConfig cfg = new CassandraConfig();
        ScpCmd cmd = new ScpCmd();
        cmd.localFile = "/etc/profile";
        cmd.remoteFile = "/tmp/profile";
        cmd.execute("192.168.200.56");
    }

}