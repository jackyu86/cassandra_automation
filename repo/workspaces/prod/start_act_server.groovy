import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]

remotePath = "/export/App/act_server/bin"

SshFactory.getSSH(host).exec("cd "+remotePath+"; ./start.sh act stag",100);

System.exit(0)
