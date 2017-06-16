package com.jd.jg.admin.cmd;

import com.jd.jg.admin.ssh.SshFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * delete all files in the path or create path when not exist
 */
public class CleanPathCmd implements ICommand{

    Logger logger = LoggerFactory.getLogger(CleanPathCmd.class);
    public String path = null;

    @Override
    public void execute(String host) {
        SshFactory.getSSH(host).exec("LANG=C mkdir -p " + path, 30).errorDetect("cannot create directory");
        SshFactory.getSSH(host).exec("/bin/rm -rf "+path+"/*",60);
        //SshFactory.getSSH(host).exec("LANG=C ls -l "+path,60).expect("total 0");

        logger.info("Path {} cleaned on {}", path,host);
    }

}
