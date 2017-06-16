package com.jd.jg.admin.cmd;

import com.jd.jg.admin.ssh.SshFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScpAndTarCmd implements ICommand{

    Logger logger = LoggerFactory.getLogger(ScpAndTarCmd.class);

    public String localFile;
    public String remotePath;

    @Override
    public void execute(String host) {
        ScpCmd cmd = new ScpCmd();

        int i = localFile.lastIndexOf("/");
        String fileName = localFile.substring(i+1);

        cmd.localFile = localFile;
        cmd.remoteFile = "/tmp/"+fileName;
        cmd.execute(host);

        if( remotePath.endsWith("/")){
            remotePath = remotePath.substring(0,remotePath.length()-1);
        }

        i = remotePath.lastIndexOf("/");

        String tgtPath = remotePath.substring(0,i);
        String untarPath = remotePath.substring(i+1);

        CleanPathCmd c1 = new CleanPathCmd();
        c1.path = remotePath;
        c1.execute(host);

        logger.info("{} copied to /tmp/{} on {}",localFile,fileName,host);

        if( fileName.endsWith("zip")){
            SshFactory.getSSH(host).exec("unzip -o /tmp/" + fileName + " -d " + tgtPath, 1200);
        }
        else {
            SshFactory.getSSH(host).exec("tar -xzf /tmp/" + fileName + " -C " + tgtPath, 1200);
        }

        // verify
        SshFactory.getSSH(host).exec("ls -l "+tgtPath,60).expect(untarPath);

        logger.info("/tmp/{} extracted to {} on {}",fileName,tgtPath,host);
    }
}
