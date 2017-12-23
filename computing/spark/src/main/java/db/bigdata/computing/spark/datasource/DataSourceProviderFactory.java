package db.bigdata.computing.spark.datasource;

import org.apache.spark.api.java.JavaSparkContext;

import java.util.Optional;

public class DataSourceProviderFactory {
    public static SparkDataSource getDataSource(JavaSparkContext sparkContext, SparkDataSource.SparkDataSourceType sparkDataSourceType, Optional<String> source) {
        switch (sparkDataSourceType) {
            case TEXT_FILE_DATA_SOURCE:
                return new TextFileDataSource(sparkContext, source.get());
        }
        throw new RuntimeException("DataSource is not supported " + sparkDataSourceType.name());
    }
}
