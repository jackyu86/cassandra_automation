package com.jd.jg.admin.cmd;

import com.jd.jg.admin.ssh.SshFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MakeDirCmd implements ICommand{

    Logger logger = LoggerFactory.getLogger(MakeDirCmd.class);
    public String path;

    @Override
    public void execute(String host) {
        SshFactory.getSSH(host).exec("LANG=C mkdir -p " + path, 30).errorDetect("cannot create directory");
        // SshFactory.getSSH(host).exec("LANG=C ls -l "+path,60).expect("total");

        logger.info("Path {} created on {}",path,host);
    }
}
