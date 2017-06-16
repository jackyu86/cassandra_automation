package com.jd.jg.admin.cmd;

import com.jd.jg.admin.config.CassandraConfig;
import com.jd.jg.admin.ssh.SshFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartCassandraCmd implements ICommand{

    Logger logger = LoggerFactory.getLogger(StartCassandraCmd.class);

    public CassandraConfig cfg;

    @Override
    public void execute(String host) {
        MakeDirCmd cmd = new MakeDirCmd();
        cmd.path = cfg.commitlog_directory;
        cmd.execute(host);

        cmd.path = cfg.data_file_directory;
        cmd.execute(host);

        String metricsCfg = "";
        if(StringUtils.isNotBlank(cfg.ganglia_url)){
            metricsCfg = " -Dcassandra.metricsReporterConfigFile=metrics.yaml ";
        }

        String javaHomeSetting = "";
        if( StringUtils.isNotBlank(cfg.java_home)){
            javaHomeSetting = " JAVA_HOME='"+cfg.java_home+"' ";
        }

        SshFactory.getSSH(host).exec( javaHomeSetting + cfg.installPath+"/bin/cassandra "+metricsCfg,120).expect("No gossip backlog; proceeding");

        logger.info("cassandra started on {}",host);
    }
}
