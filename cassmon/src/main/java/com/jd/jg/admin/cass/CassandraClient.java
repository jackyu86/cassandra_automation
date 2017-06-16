package com.jd.jg.admin.cass;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraClient {

    static Logger logger = LoggerFactory.getLogger(CassandraClient.class);

    Cluster cluster;
    Session session = null;
    int batchSize = 200;

    public void init(String seed,int batchSize){
        cluster = Cluster.builder().addContactPoint(seed).build();
        cluster.getConfiguration().getQueryOptions().setConsistencyLevel(ConsistencyLevel.QUORUM);
        cluster.getConfiguration().getSocketOptions().setReadTimeoutMillis(600000);
        session = cluster.newSession();
        this.batchSize = batchSize;
    }

    public Session getSession(){
        return session;
    }

    public void close() {
        if (cluster != null) {
            cluster.close();
        }
        if (session != null) {
            session.close();
        }
    }
}
