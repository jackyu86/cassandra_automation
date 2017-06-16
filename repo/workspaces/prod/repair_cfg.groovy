import com.jd.jg.admin.task.*;
import com.jd.jg.admin.cmd.*;
import com.jd.jg.admin.ssh.SshFactory;

new File(args[0]).eachLine{
    def host = it
    println host
    SshFactory.getSSH(host).exec('JAVA_HOME=/export/servers/jdk1.7.0_71/ /export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/nodetool repair cfg',20)
    
}

System.exit(0)
