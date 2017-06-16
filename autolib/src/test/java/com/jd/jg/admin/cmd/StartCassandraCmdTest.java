package com.jd.jg.admin.cmd;

import com.jd.jg.admin.config.CassandraConfig;
import org.junit.Test;

/**
 * Created by zhouji on 16-4-13.
 */
public class StartCassandraCmdTest {

    @Test
    public void testExecute() throws Exception {
        CassandraConfig cfg = new CassandraConfig();
        StartCassandraCmd cmd = new StartCassandraCmd();
        cmd.cfg = cfg;
        cfg.installPath = "/export/cassandra/apache-cassandra-2.1.9/bin";
        cmd.execute("192.168.200.56");
    }

}