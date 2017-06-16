package com.jd.jg.admin.cmd;

import com.jd.jg.admin.config.CassandraConfig;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by zhouji on 16-4-13.
 */
public class ScpTemplateCmdTest {
    @Test
    public void execute() throws Exception {
        CassandraConfig cfg = new CassandraConfig();
        cfg.clusterName = "OMyCluster";
        String host = "192.168.200.56";

        ScpTemplateCmd cmd = new ScpTemplateCmd();
        cmd.tplName = "cassandra.yaml.ftl";
        cmd.tplPath = "/home/zhouji/jd/projects/utils/src/main/resources/template/";
        cmd.remoteFile = "/tmp/cassandra.yaml";
        cmd.model = new HashMap<>();
        cmd.model.put("cfg",cfg);
        cmd.model.put("host",host);

        cmd.execute(host);
    }

}