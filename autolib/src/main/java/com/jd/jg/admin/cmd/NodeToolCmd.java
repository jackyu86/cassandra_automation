package com.jd.jg.admin.cmd;

import com.jd.jg.admin.config.CassandraConfig;
import com.jd.jg.admin.ssh.SshFactory;
import com.jd.jg.admin.ssh.SshResult;

public abstract class NodeToolCmd implements ICommand{

    public CassandraConfig cfg;

    public abstract void execute(String host);

    public SshResult nodeToolRun(String host, String cmd){
        String nodetool = "LANG=C JAVA_HOME="+cfg.java_home+" "+cfg.installPath + "/bin/nodetool -u controlRole -pw "+cfg.jmxPassword+" ";
        SshResult r = SshFactory.getSSH(host).exec(nodetool + cmd, 60);
        return r;
    }
}
