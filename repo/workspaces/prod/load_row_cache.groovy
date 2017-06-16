import com.jd.jg.admin.task.*;
import com.jd.jg.admin.cmd.*;
import com.jd.jg.admin.ssh.SshFactory;

new File(args[0]).eachLine{
    def host = it
    def t = Thread.start{
        SshFactory.getSSH(host).exec('JAVA_HOME=/export/servers/jdk1.7.0_71/ /export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/nodetool loadrowcache sr activity_profiles_brand_seckill',20)
        println host
    }
}

Thread.sleep(5*60000)
System.exit(0)
