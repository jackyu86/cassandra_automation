package com.jd.jg.admin.task;

import com.alibaba.fastjson.JSON;
import com.jd.jg.admin.config.CassandraConfig;
import com.jd.jg.admin.config.SystemConfig;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CassandraTasksTest {

    @Test
    public void install() throws Exception {
        String seed = "192.168.200.67";
        String host = "192.168.200.67";

        String cassCfg = new String(Files.readAllBytes(Paths.get("../repo/config/dev.67.json")));

        CassandraConfig cfg = JSON.parseObject(cassCfg,CassandraConfig.class);

        String sysCfg = new String(Files.readAllBytes(Paths.get("../repo/config/sys.json")));
        SystemConfig scfg = JSON.parseObject(sysCfg,SystemConfig.class);

        CassandraTasks tasks = new CassandraTasks();
        tasks.cassCfg = cfg;
        tasks.syscfg = scfg;

        tasks.stopCassandra(host);

        //tasks.cleanDataAndLog(host);

        //tasks.install(host);

        tasks.updateCassandraEnvSh(host);
        tasks.updateCassandraYaml(host);

        tasks.startCassandra(host);

    }

}