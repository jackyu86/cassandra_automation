import com.alibaba.fastjson.JSON;
import com.jd.jg.admin.config.*;
import com.jd.jg.admin.task.*;

import java.nio.file.Files;
import java.nio.file.Paths;

String host = args[0]
String dc = args[1]

boolean cleanData = false
if( args[2].equals("true")){
    cleanData = true
}

String cassCfg = new String(Files.readAllBytes(Paths.get("./config/"+dc+".json")));

def cfg = JSON.parseObject(cassCfg,CassandraConfig.class);

String sysCfg = new String(Files.readAllBytes(Paths.get("./config/sys.json")));
def scfg = JSON.parseObject(sysCfg,SystemConfig.class);

def tasks = new CassandraTasks();
tasks.cassCfg = cfg;
tasks.syscfg = scfg;

tasks.stopCassandra(host);

if( cleanData ){
    tasks.cleanDataAndLog(host);
}

tasks.install(host);

tasks.updateCassandraEnvSh(host);
tasks.updateCassandraYaml(host);
tasks.updateMetricsYaml(host);
tasks.updateRackDc(host);

tasks.startCassandra(host);

System.exit(0)
