package com.jd.jg.admin.cmd;

import com.jd.jg.admin.ssh.SshFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScpCmd implements ICommand{

    Logger logger = LoggerFactory.getLogger(ScpCmd.class);

    public String localFile;
    public String remoteFile;

    public String uid;
    public String password;

    @Override
    public void execute(String host) {
        if(StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(password)) {
            SshFactory.getSSH(host,uid,password).scp(localFile, remoteFile);
        }
        else{
            SshFactory.getSSH(host).scp(localFile, remoteFile);
        }
        /*
        String md5 = LocalCmdUtils.exec("md5",localFile).substring(0,32);
        SshFactory.getSSH(host).exec("md5 "+remoteFile,30).expect(md5);
        */

        logger.info("{} copied to {} on {}",localFile,remoteFile,host);
    }
}
