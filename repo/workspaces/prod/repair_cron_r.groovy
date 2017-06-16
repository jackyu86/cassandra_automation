import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

f=args[0]
dc=args[1]
ks=args[2]
def repair(dc,host,ks){
    println "repair $ks on $host"
    def remotePath = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin"
    def cmd = "python range_repair.py -H 127.0.0.1 -k ${ks} -D ${dc} -s 3 -l -v -n ./nodetool -N ${host} --logfile=/tmp/${ks}.log"
    def ssh = SshFactory.getSSH(host)
    ssh.exec("cd "+remotePath+";JAVA_HOME=/export/servers/jdk1.7.0_71/ "+cmd,100);
    ssh.exec("cd "+remotePath+";JAVA_HOME=/export/servers/jdk1.7.0_71/ ./nodetool clearsnapshot",100);
    ssh.close()
}

new File(f).readLines().reverse().each{ 
    repair(dc,it,ks)
}

