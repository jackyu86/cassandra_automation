import com.jd.jg.admin.cmd.*
import com.jd.jg.admin.task.*;
import com.jd.jg.admin.ssh.SshFactory;

def deploy(host){
    ScpCmd scp = new ScpCmd()
    scp.localFile = "../../install/jolokia-jvm.jar"
    scp.remoteFile = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/lib/jolokia-jvm.jar"
    scp.execute(host)
    
    scp.localFile = "../../install/telegraf"
    scp.remoteFile = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/telegraf"
    scp.execute(host)
    
    SshFactory.getSSH(host).exec("chmod +x /export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/telegraf",200)
}

def hosts = []
def hosts_dc1 = []
def hosts_dc2 = []
def dc1 = []
dc1.addAll(29..51)
dc1.each{
    hosts_dc1.add("172.20.107."+it)
}

def dc21 = [110,150]
dc21.addAll(152..160)
dc21.each{
    hosts_dc2.add("172.28.147."+it)
}

def dc22 = []
dc22.addAll(11..29)
dc22.each{
    hosts_dc2.add("172.28.148."+it)
}

hosts.addAll(hosts_dc1)
hosts.addAll(hosts_dc2)

['172.20.107.21'].each{
  deploy(it)
}

System.exit(0)
