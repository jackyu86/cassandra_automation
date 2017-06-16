import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]

remotePath = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/"

SshFactory.getSSH(host).exec("cd "+remotePath+"; JAVA_HOME=/export/servers/jdk1.7.0_71 ./nodetool rebuild -- DC1",100);

System.exit(0)
