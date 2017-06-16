package com.jd.jg.admin.cmd;

import com.jd.jg.admin.ssh.SshFactory;
import com.jd.jg.admin.ssh.SshResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckProcessCmd implements ICommand{

    Logger logger = LoggerFactory.getLogger(CheckProcessCmd.class);

    public String processName;
    public String expect;

    @Override
    public void execute(String host) {
        SshFactory.getSSH(host).exec("ps -ef | grep "+processName+" | grep -v grep",15).expect(expect);
        logger.info("process {} exists on {}",processName,host);
    }
}
