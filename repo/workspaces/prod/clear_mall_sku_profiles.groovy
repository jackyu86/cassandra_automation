import com.jd.jg.admin.cmd.ScpAndTarCmd;
import com.jd.jg.admin.config.SystemConfig;
import com.jd.jg.admin.ssh.SshFactory;

def host = args[0]


SshFactory.getSSH(host).exec("/bin/rm -rf /export/Data/cassandra/data/sr/mall_sku_profiles-*",100);

System.exit(0)
