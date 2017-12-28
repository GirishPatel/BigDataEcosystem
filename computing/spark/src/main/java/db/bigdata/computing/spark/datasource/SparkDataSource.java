package db.bigdata.computing.spark.datasource;

import org.apache.spark.api.java.JavaRDD;

public interface SparkDataSource<T> {
    public JavaRDD<T> getRDD(int minPartitions);

    enum SparkDataSourceType {
        TEXT_FILE_DATA_SOURCE
    }
}
