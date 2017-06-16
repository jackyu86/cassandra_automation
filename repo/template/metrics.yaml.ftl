ganglia:
  -
    period: 60
    timeunit: 'SECONDS'
    prefix: '${cfg.clusterName}'
    hosts:
     - host: '${cfg.ganglia_url}'
       port: 8649
    predicate:
      color: "white"
      useQualifiedName: true
      patterns:
        - "^org.apache.cassandra.metrics.Cache.+"
        - "^org.apache.cassandra.metrics.ClientRequest.+"
        - "^org.apache.cassandra.metrics.Storage.+"
        - "^org.apache.cassandra.metrics.ThreadPools.+"
        - "^org.apache.cassandra.metrics.Latency.+"
        - "^org.apache.cassandra.metrics.Connection.+"
        - "^org.apache.cassandra.metrics.CQL.+"
        - "^org.apache.cassandra.metrics.SEP.+"
        - "^org.apache.cassandra.metrics.ColumnFamily.+"
