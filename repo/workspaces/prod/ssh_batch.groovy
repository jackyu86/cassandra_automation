import com.jd.jg.admin.cmd.*
import com.jd.jg.admin.task.*;
import com.jd.jg.admin.ssh.SshFactory;

def deploy(host){
    SshFactory.getSSH(host).exec('export JAVA_HOME=/export/servers/jdk1.7.0_71/; cd /export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/; echo "set -b org.apache.cassandra.db:type=Caches RowCacheCapacityInMB 81920" | /export/servers/jdk1.7.0_71/bin/java -jar jmxterm-1.0-alpha-4-uber.jar --url localhost:7199',200)
}


new File(args[0]).eachLine{
  deploy(it)
}

System.exit(0)
