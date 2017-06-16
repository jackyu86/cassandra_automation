package com.jd.jg.admin.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopCassandraCmd extends NodeToolCmd{

    Logger logger = LoggerFactory.getLogger(StopCassandraCmd.class);

    @Override
    public void execute(String host){
        try {
            CheckProcessCmd cmd = new CheckProcessCmd();
            cmd.processName = "cassandra";
            cmd.expect = "java";
            cmd.execute(host);
        }catch (RuntimeException ex){
            logger.info("No cassandra process find in {}",host);
            return;
        }

        nodeToolRun(host,"disablebinary");
        logger.info("Stop binary protocol on {}",host);

        nodeToolRun(host,"stopdaemon").expect("Cassandra has shutdown");
        logger.info("Stop cassandra process on {} successfully.",host);
    }

}
