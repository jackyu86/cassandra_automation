import com.jd.jg.admin.cmd.*;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;
import com.jd.jg.admin.util.CassandraClient;

def hosts = []

def keyspaces = ['ur','cfg','sr','catesr','rktsr']

seeds = "172.19.153.48"
cass = new CassandraClient();
cass.init(seeds)
session = cass.getSession()


def deploy_repair_script(host){
    ScpCmd scp = new ScpCmd()
    scp.localFile = "../../install/range_repair.py"
    scp.remoteFile = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/range_repair.py"
    scp.execute(host)
}

['172.28.147.110','172.28.147.150','172.28.147.152'].each{ h ->
    deploy_repair_script(h)
    keyspaces.each{ ks ->
        session.execute("insert into cfg.repair_job_info (ip,ks,dc) values (?,?,?) if not exists",h,ks,'DC1')
    }
}
System.exit(0)



