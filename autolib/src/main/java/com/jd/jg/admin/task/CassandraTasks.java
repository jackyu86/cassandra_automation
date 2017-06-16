package com.jd.jg.admin.task;

import com.jd.jg.admin.cmd.*;
import com.jd.jg.admin.config.CassandraConfig;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class CassandraTasks {

    Logger logger = LoggerFactory.getLogger(CassandraTasks.class);

    CassandraConfig cassCfg;

    SystemConfig syscfg;

    public void stopCassandra(String host){
        CheckProcessCmd c0 = new CheckProcessCmd();
        c0.processName = "java";
        c0.expect = "apache-cassandra-";

        try {
            c0.execute(host);
        }catch (SshResult.ExpectException ex) {
            logger.info("cassandra process not exist.");
            return;
        }

        StopCassandraCmd c1 = new StopCassandraCmd();
        c1.cfg = cassCfg;
        c1.execute(host);
    }

    public void startCassandra(String host){
        StartCassandraCmd cmd = new StartCassandraCmd();
        cmd.cfg = cassCfg;

        cmd.execute(host);
    }

    public void cleanOldInstall(String host){
        CleanPathCmd cmd = new CleanPathCmd();
        cmd.path = cassCfg.installPath;
        cmd.execute(host);
    }

    public void cleanDataAndLog(String host){
        CleanPathCmd cmd = new CleanPathCmd();
        cmd.path = cassCfg.commitlog_directory;
        cmd.execute(host);

        cmd.path = cassCfg.data_file_directory;
        cmd.execute(host);
    }

    public void updateConfig(String tplFile,String tgtFile, String host){
        ScpTemplateCmd cmd = new ScpTemplateCmd();
        cmd.tplPath = syscfg.templatePath;
        cmd.tplName = tplFile;
        cmd.remoteFile = tgtFile;

        cmd.model = new HashMap<>();
        cmd.model.put("cfg", cassCfg);
        cmd.model.put("local_ip",host);

        cmd.execute(host);
    }

    public void updateCassandraYaml(String host){
        updateConfig("cassandra.yaml.ftl", cassCfg.installPath+"/conf/cassandra.yaml",host);
    }

    public void updateCassandraEnvSh(String host){
        updateConfig("cassandra-env.sh.ftl", cassCfg.installPath+"/conf/cassandra-env.sh",host);
    }

    public void updateMetricsYaml(String host){
        updateConfig("metrics.yaml.ftl", cassCfg.installPath+"/conf/metrics.yaml",host);
    }

    public void updateRackDc(String host){
        updateConfig("cassandra-rackdc.properties.ftl", cassCfg.installPath+"/conf/cassandra-rackdc.properties",host);
    }

    public void install(String host){
        ScpAndTarCmd cmd = new ScpAndTarCmd();
        cmd.localFile = cassCfg.installPkg;
        cmd.remotePath = cassCfg.installPath;

        cmd.execute(host);
    }


}
