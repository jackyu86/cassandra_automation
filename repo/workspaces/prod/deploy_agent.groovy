import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]


ScpAndTarCmd cmd = new ScpAndTarCmd();
cmd.localFile = "../../install/autodeploy_agent.tar.gz"
cmd.remotePath = "/export/servers/autodeploy_agent/"
cmd.execute(host)


SshFactory.getSSH(host,"admin","vM899&a97q0g").exec("/export/servers/autodeploy_agent/bin/install_autodeploy_client.sh",100)

System.exit(0)
