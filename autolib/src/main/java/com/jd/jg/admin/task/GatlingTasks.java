package com.jd.jg.admin.task;

import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

public class GatlingTasks {

    SystemConfig syscfg;

    public void install(String host){
        ScpAndTarCmd cmd = new ScpAndTarCmd();
        cmd.localFile = syscfg.gatling_pkg;
        cmd.remotePath = syscfg.gatling_installpath;

        cmd.execute(host);
    }

    public void startTest(String host,String testName, int qps){
        SshFactory.getSSH(host).exec("",100);
    }

    public void stopTest(String host){

    }
}
