import com.alibaba.fastjson.JSON;
import com.jd.jg.admin.config.*;
import com.jd.jg.admin.task.*;

import java.nio.file.Files;
import java.nio.file.Paths;


String cassCfg = new String(Files.readAllBytes(Paths.get("./onfig/lf.main.json")));

def cfg = JSON.parseObject(cassCfg,CassandraConfig.class);

String sysCfg = new String(Files.readAllBytes(Paths.get("./config/sys.json")));
def scfg = JSON.parseObject(sysCfg,SystemConfig.class);

def tasks = new CassandraTasks();
tasks.cfg = cfg;
tasks.syscfg = scfg;

def clusterTasks = new CassandraClusterTasks(cfg,scfg);
clusterTasks.ct = tasks;

clusterTasks.updateCassandraConfig()

System.exit(0)
