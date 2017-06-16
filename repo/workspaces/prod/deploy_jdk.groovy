import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;

def host = args[0]

ScpAndTarCmd cmd = new ScpAndTarCmd();
cmd.localFile = "../../install/jdk1.7.0_71.tar.gz"
cmd.remotePath = "/export/servers/jdk1.7.0_71/"

cmd.execute(host)
System.exit(0)
