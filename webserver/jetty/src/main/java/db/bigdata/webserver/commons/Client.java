package db.bigdata.webserver.commons;

public enum Client {

    // relational
    MYSQL,

    //coordination
    ZOOKEEPER,

    // cache
    REDIS,
    HAZELCAST,

    // storage
    CASSANDRA,
    HADOOP,
    HBASE,

    // search
    SOLR,
    ELASTICSEARCH,

    // webserver
    JETTY,

    // queue
    RMQ,
    KAFKA,

    // computing
    AKKA,
    STORM,
    SPARK,

    // monitoring
    COSMOS,
    HYSTRIX

}
