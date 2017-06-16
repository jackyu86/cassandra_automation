package com.jd.jg.admin.task;

import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.CassandraConfig;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GangliaTasks {

    Logger logger = LoggerFactory.getLogger(GangliaTasks.class);

    SystemConfig syscfg;

    public void install(String host){
        ScpAndTarCmd cmd = new ScpAndTarCmd();
        cmd.localFile = syscfg.ganglia_pkg;
        cmd.remotePath = syscfg.ganglia_installpath;

        cmd.execute(host);
    }

    public void start(String host){
        SshFactory.getSSH(host).exec(syscfg.ganglia_installpath+"/sbin/gmond",300);
    }
}
