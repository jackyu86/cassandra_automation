import com.jd.jg.admin.cmd.*

def deploy_repair_script(host){
    ScpCmd scp = new ScpCmd()
    scp.localFile = "../../install/range_repair.py"
    scp.remoteFile = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/range_repair.py"
    scp.execute(host)
}


['172.20.107.21'].each{
  deploy_repair_script(it)
}

System.exit(0)
