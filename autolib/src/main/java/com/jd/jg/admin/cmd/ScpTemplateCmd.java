package com.jd.jg.admin.cmd;

import com.jd.jg.admin.ssh.SshFactory;
import com.jd.jg.admin.util.TemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ScpTemplateCmd implements ICommand {

    Logger logger = LoggerFactory.getLogger(ScpTemplateCmd.class);

    public String tplPath;
    public String tplName;
    public String remoteFile;
    public Map<String,Object> model;

    @Override
    public void execute(String host) {
        String content = TemplateUtils.merge(tplPath,tplName,model);
        String tmpFilePath = "/tmp/"+tplName+"."+System.nanoTime();
        File tmpFile = new File(tmpFilePath);
        FileWriter fw = null;
        try {
            fw = new FileWriter(tmpFile);
            fw.write(content);
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException("failed to create local tmp file",e);
        } finally {
            if( fw != null) {
                try {fw.close();} catch (IOException e) {}
            }
        }

        logger.info("{} generated {}",tplName,tmpFile);

        SshFactory.getSSH(host).scp(tmpFilePath,remoteFile);
        /*
        String md5 = LocalCmdUtils.exec("md5",tmpFilePath).substring(0,32);
        SshFactory.getSSH(host).exec("md5 "+remoteFile,30).expect(md5);
        */
        logger.info("{} copied to {} on {}",tmpFile,remoteFile,host);
    }
}
