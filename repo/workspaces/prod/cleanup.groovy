import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

remotePath = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin"

new File(args[0]).eachLine{
  println it
  SshFactory.getSSH(it).exec("cd "+remotePath+"; JAVA_HOME=/export/servers/jdk1.7.0_71 ./nodetool cleanup",100);
}

System.exit(0)
