import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;
import com.jd.jg.admin.util.CassandraClient;

def hosts = []
def hosts_dc1 = []
def hosts_dc2 = []
def dc1 = [11,24,23,56]
dc1.addAll(26..51)
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

def keyspaces = ['ur','cfg','sr','catesr','rktsr']

seeds = "172.19.153.48"
cass = new CassandraClient();
cass.init(seeds)
session = cass.getSession()

/*
['172.20.107.26','172.20.107.27'].each{ h ->
    keyspaces.each{ ks ->
        session.execute("insert into cfg.repair_job_info (ip,ks,dc) values (?,?,?) if not exists",h,ks,'DC1')
    }
}
System.exit(0)
*/


jobCounter = 0;
hosts.each{ h ->
    rs = session.execute("select * from cfg.repair_job_info where ip=?",h)
    jobs = []
    rs.all().each{ r ->
        ri = new RepairInfo()
        ri.host = r.getString('ip')
        ri.ks = r.getString('ks')
        ri.dc = r.getString('dc')
        ri.lastStartTime = r.getTimestamp('last_start_time')
        ri.lastEndTime = r.getTimestamp('last_end_time')
        jobs.add(ri)
    }
    // find the repaired ks
    // each node can only has one running repair session.
    def rj = getRepairedKs(jobs)
    if( rj != null){
	if( jobCounter > 5){
	    return
	}
	jobCounter++;
        println "repair ${rj.dc} ${rj.host} ${rj.ks}"
	def t = Thread.start{
            repair(rj.dc,rj.host,rj.ks)
	}
    }
}

class RepairInfo{
    String host;
    String ks;
    String dc;
    Date lastStartTime;
    Date lastEndTime;
}

def getRepairedKs(jobs){
    if( jobs == null || jobs.size() == 0)return null;
    def job;
    def runningJob;
    jobs.each{ j ->
	if( j.lastStartTime == null ){
	    job = j;
	    return
        }
	if( j.lastEndTime == null ){
	    runningJob = j
            return
        }
        if( j.lastStartTime.getTime() > j.lastEndTime.getTime()){
            // may have running repair session
	    runningJob = j
            if( System.currentTimeMillis() - j.lastStartTime.getTime()  < 86400000 ){
                // wait for 1 day for this session to complete
                return;
            }
            else{
                println "repair session last for more than 1 day:  ${j.host} ${j.ks}, just ignore it."
                runningJob = null
                job = j
            }
        }
        if( job == null ){
            job = j
        }
        else if( job.lastEndTime != null && j.lastEndTime != null && job.lastEndTime.getTime() > j.lastEndTime.getTime()){
            job = j;
        }
    }
	
    if( runningJob != null){
        println "repair ${runningJob.host} ${runningJob.ks} is still running"
        return null
    }

    if( job ==null || job.lastStartTime == null ) return job
    def timeDt = new Date().getTime() - job.lastEndTime.getTime()
    if( timeDt > 3*86400000){
        return job;
    }
    else{
	dt = timeDt / 1000 / 60 / 60
        printf("%s timeDt is %3.2f hours, no need to repair.\n",job.host, dt)
        return null
    }
}

def repair(dc,host,ks){
    def remotePath = "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin"
    def cmd = "python range_repair.py -H 127.0.0.1 -k ${ks} -D ${dc} -s 10 -l -v -n ./nodetool -N ${host} --logfile=/tmp/${ks}.log"
    def ssh = SshFactory.getSSH(host)
    ssh.exec("cd "+remotePath+";JAVA_HOME=/export/servers/jdk1.7.0_71/ "+cmd,100);
    ssh.close()
}

Thread.sleep(60000)
System.exit(0)

