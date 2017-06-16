import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]

//remotePath = "/export/App/"

//SshFactory.getSSH(host).exec("cd "+remotePath+";PATH=/export/servers/jdk1.7.0_71/bin:\$PATH ./stop.sh",100);

ScpAndTarCmd cmd = new ScpAndTarCmd();
cmd.localFile = "../../install/gatling.tgz"
cmd.remotePath = "/export/App/gatling-charts-highcharts-bundle-2.1.7"

cmd.execute(host)
System.exit(0)
