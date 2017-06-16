import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]

remotePath = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/"

SshFactory.getSSH(host).exec("killall -9 java",100);

System.exit(0)
