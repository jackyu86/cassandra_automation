package com.jd.jg.admin;

import com.jd.jg.admin.cass.CassandraClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${cassandra.seeds}")
    String seeds;
    @Value("${cassandra.fetchSize}")
    int fetchSize;

    @Bean
    public CassandraClient cassandraClient() {
        CassandraClient cass = new CassandraClient();
        cass.init(seeds,200);
        return cass;
    }
}
