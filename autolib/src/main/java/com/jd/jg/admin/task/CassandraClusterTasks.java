package com.jd.jg.admin.task;

import com.alibaba.fastjson.JSON;
import com.jd.jg.admin.config.CassandraConfig;
import com.jd.jg.admin.util.Ping;
import com.jd.jg.admin.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CassandraClusterTasks {

    Logger logger = LoggerFactory.getLogger(CassandraClusterTasks.class);

    CassandraConfig cfg;
    CassandraTasks ct;
    SystemConfig syscfg;

    public CassandraClusterTasks(CassandraConfig cfg, SystemConfig syscfg){
        this.cfg = cfg;
        this.syscfg = syscfg;
        ct = new CassandraTasks();
        ct.cassCfg = cfg;
        ct.syscfg = syscfg;
    }

    public boolean checkAllHostConnectable(){
        boolean result = true;
        // check if all host is connectable
        for (String ip : cfg.allHosts) {
            try {
                boolean reachable = Ping.isReachable(ip);
                if (!reachable) {
                    logger.error("failed connect to {}", ip);
                    result = false;
                }
            } catch (IOException e) {
                logger.error("failed connect to {}", ip);
                result = false;
            }
        }
        return result;
    }

    public void stopAllNodes(){
        for( String host : cfg.allHosts){
            try {
                ct.stopCassandra(host);
            }catch (Exception ex){
                logger.error("failed to stop cassandra on {}",host,ex);
            }
        }
    }

    public void startAllNodes(){
        for( String host : cfg.allHosts){
            try {
                ct.startCassandra(host);
            }catch (Exception ex){
                logger.error("failed to start cassandra on {}",host,ex);
            }
        }
    }

    public void updateCassandraConfig(){
        List<String> failedHosts = new ArrayList<>();
        for( String host : cfg.allHosts){
            try {
                ct.stopCassandra(host);
            }catch (Exception ex){
                failedHosts.add(host);
                continue;
            }

            try {
                ct.updateCassandraYaml(host);
            }catch (Exception ex){
                failedHosts.add(host);
                continue;
            }

            try {
                ct.updateCassandraEnvSh(host);
            }catch (Exception ex){
                failedHosts.add(host);
                continue;
            }

            try {
                ct.updateMetricsYaml(host);
            }catch (Exception ex){
                failedHosts.add(host);
                continue;
            }

            try {
                ct.startCassandra(host);
            }catch (Exception ex){
                failedHosts.add(host);
                continue;
            }
            logger.info("{} config updated.",host);
        }
        if( failedHosts.size() == 0){
            logger.info("config updated for the whole cluster.");
        }
        else {
            logger.error("config update failed on {}.", JSON.toJSON(failedHosts));
        }
    }
}
