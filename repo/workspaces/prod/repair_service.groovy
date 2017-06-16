import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;
import com.jd.jg.admin.util.CassandraClient;

dc = args[0]
step = args[1]
def hosts = []
new File(dc.toLowerCase()).eachLine{
    hosts.add(it)
}

def repair(host,ks){
    def remotePath = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin"
    def cmd = "python range_repair.py -H 127.0.0.1 -k ${ks} -DC ${dc} -s ${step} -v -n ./nodetool -N ${host} -l --logfile=/tmp/${ks}.log"
    println cmd
    def ssh = SshFactory.getSSH(host)
    ssh.exec("cd "+remotePath+";JAVA_HOME=/export/servers/jdk1.7.0_71/ "+cmd,100);
    ssh.close()
}

repair('172.20.107.11','cfg')
System.exit(0)

