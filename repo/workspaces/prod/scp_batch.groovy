import com.jd.jg.admin.cmd.*
import com.jd.jg.admin.task.*;
import com.jd.jg.admin.ssh.SshFactory;

def deploy(host){
    ScpCmd scp = new ScpCmd()
    scp.localFile = "../../install/jmxterm-1.0-alpha-4-uber.jar"
    scp.remoteFile = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/jmxterm-1.0-alpha-4-uber.jar"
    scp.execute(host)
}


new File(args[0]).eachLine{
  deploy(it)
}

System.exit(0)
