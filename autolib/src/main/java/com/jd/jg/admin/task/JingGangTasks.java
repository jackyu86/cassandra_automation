package com.jd.jg.admin.task;

import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;

public class JingGangTasks {

    SystemConfig syscfg;

    public void installScoring(String host){

    }

    public void installJDK(String host){
        ScpAndTarCmd cmd = new ScpAndTarCmd();
        cmd.localFile = syscfg.ganglia_pkg;
        cmd.remotePath = syscfg.ganglia_installpath;
    }

}
