import com.alibaba.fastjson.JSON;
import com.jd.jg.admin.config.*;
import com.jd.jg.admin.task.*;

import java.nio.file.Files;
import java.nio.file.Paths;

String host = args[0]

String cassCfg = new String(Files.readAllBytes(Paths.get("./config/cfg.main.json")));

def cfg = JSON.parseObject(cassCfg,CassandraConfig.class);

String sysCfg = new String(Files.readAllBytes(Paths.get("./config/sys.json")));
def scfg = JSON.parseObject(sysCfg,SystemConfig.class);

def tasks = new CassandraTasks();
tasks.cfg = cfg;
tasks.syscfg = scfg;

tasks.stopCassandra(host);

tasks.cleanDataAndLog(host);

tasks.install(host);

tasks.updateCassandraEnvSh(host);
tasks.updateCassandraYaml(host);
tasks.updateMetricsYaml(host);

//tasks.startCassandra(host);

System.exit(0)
