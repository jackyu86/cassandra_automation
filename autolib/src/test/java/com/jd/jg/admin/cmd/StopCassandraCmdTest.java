package com.jd.jg.admin.cmd;

import com.jd.jg.admin.config.CassandraConfig;
import org.junit.Test;

/**
 * Created by zhouji on 16-4-13.
 */
public class StopCassandraCmdTest {

    @Test
    public void testExecute() throws Exception {
        CassandraConfig cfg = new CassandraConfig();

        StopCassandraCmd cmd = new StopCassandraCmd();
        cmd.cfg = cfg;
        cfg.installPath = "/export/cassandra/apache-cassandra-2.1.9";
        cmd.execute("192.168.200.56");
    }

}