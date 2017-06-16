import com.alibaba.fastjson.JSON;
import com.jd.jg.admin.config.*;
import com.jd.jg.admin.task.*;

import java.nio.file.Files;
import java.nio.file.Paths;

String host = args[0]
String cluster = args[1]
String cassCfg = new String(Files.readAllBytes(Paths.get("./config/"+cluster+".json")));

def cfg = JSON.parseObject(cassCfg,CassandraConfig.class);

String sysCfg = new String(Files.readAllBytes(Paths.get("./config/sys.json")));
def scfg = JSON.parseObject(sysCfg,SystemConfig.class);

def tasks = new CassandraTasks();
tasks.cfg = cfg;
tasks.syscfg = scfg;

tasks.updateCassandraEnvSh(host)
tasks.updateCassandraYaml(host)
tasks.updateRackDc(host)

System.exit(0)
