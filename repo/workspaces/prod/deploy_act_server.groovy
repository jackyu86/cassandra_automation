import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]

remotePath = "/export/App/act_server/bin"

SshFactory.getSSH(host).exec("cd "+remotePath+";PATH=/export/servers/jdk1.7.0_71/bin:\$PATH ./stop.sh",100);

ScpAndTarCmd cmd = new ScpAndTarCmd();
cmd.localFile = "../../install/act-scoring-1.0-SNAPSHOT-assembly.zip"
cmd.remotePath = "/export/App/act_server/act_server/"

cmd.execute(host)


ScpAndTarCmd scpModel = new ScpAndTarCmd();
scpModel.localFile = "../../install/data.tgz"
scpModel.remotePath = "/export/App/act_server/data/"
scpModel.execute(host)


SshFactory.getSSH(host).exec("cd "+remotePath+";PATH=/export/servers/jdk1.7.0_71/bin/:\$PATH ./start.sh act perf",100);


System.exit(0)
