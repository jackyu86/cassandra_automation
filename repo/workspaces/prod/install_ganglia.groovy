import com.alibaba.fastjson.JSON;
import com.jd.jg.admin.config.*;
import com.jd.jg.admin.task.*;

import java.nio.file.Files;
import java.nio.file.Paths;

String host = args[0]

String sysCfg = new String(Files.readAllBytes(Paths.get("./config/sys.json")));
def scfg = JSON.parseObject(sysCfg,SystemConfig.class);

def tasks = new GangliaTasks();
tasks.syscfg = scfg;

tasks.install(host)
tasks.start(host)

System.exit(0)
