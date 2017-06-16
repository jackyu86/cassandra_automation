import com.jd.jg.admin.task.*;
import com.jd.jg.admin.cmd.*;
import com.jd.jg.admin.ssh.SshFactory;

ks=args[1]
table=args[2]

new File(args[0]).eachLine{
    def host = it
    println host
    SshFactory.getSSH(host).exec("JAVA_HOME=/export/servers/jdk1.7.0_71/ /export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/nodetool repair -dcpar -full -j 4 -- $ks $table",20)
    
}

System.exit(0)
