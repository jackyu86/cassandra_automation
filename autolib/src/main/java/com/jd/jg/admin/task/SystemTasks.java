package com.jd.jg.admin.task;

import com.jd.jg.admin.cmd.MakeDirCmd;
import com.jd.jg.admin.cmd.ScpCmd;
import com.jd.jg.admin.ssh.SshFactory;

public class SystemTasks {

    public void changePassword(String host, String uid, String rootPasswd, String newPasswd){
        SshFactory.getSSH(host,"root",rootPasswd).exec("echo \""+uid+":"+newPasswd+"\" | /usr/sbin/chpasswd ",30);
    }

    public void distributeKey(String host, String uid, String password, String localKeyPath){
        MakeDirCmd sshDir = new MakeDirCmd();
        sshDir.path = "/home/"+uid+"/.ssh";
        sshDir.execute(host);

        ScpCmd scp = new ScpCmd();
        scp.localFile = localKeyPath;
        scp.remoteFile = "/home/"+uid+"/.ssh/authorized_keys";

        scp.execute(host);

        SshFactory.getSSH(host,uid,password).exec("chmod 700 /home/"+uid+"/.ssh/",100);
        SshFactory.getSSH(host,uid,password).exec("chmod 600 "+scp.remoteFile,100);
    }
}
