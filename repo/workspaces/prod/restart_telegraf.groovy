import com.jd.jg.admin.cmd.*
import com.jd.jg.admin.task.*;
import com.jd.jg.admin.ssh.SshFactory;

def host=args[0]

SshFactory.getSSH(host).exec('killall -9 telegraf',200)
SshFactory.getSSH(host).exec('nohup /export/App/jgcass.jd.local/apache-cassandra-2.2.3/bin/telegraf -config /export/App/jgcass.jd.local/apache-cassandra-2.2.3/conf/telegraf.conf &>/dev/null &',5)

System.exit(1)
