import com.jd.jg.admin.cmd.*

def deploy(host){
    ScpCmd scp = new ScpCmd()
    scp.localFile = "../../install/jolokia-jvm.jar"
    scp.remoteFile = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/lib/jolokia-jvm.jar"
    scp.execute(host)
}

def hosts = []
def hosts_dc1 = []
def hosts_dc2 = []
def dc1 = [11,24]
dc1.addAll(28..51)
dc1.each{
    hosts_dc1.add("172.20.107."+it)
}

def dc21 = []
dc21.addAll(153..160)
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

['172.20.107.11'].each{
  deploy(it)
}

System.exit(0)
