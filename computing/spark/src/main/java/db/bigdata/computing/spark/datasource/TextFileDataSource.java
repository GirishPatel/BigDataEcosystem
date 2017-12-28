package db.bigdata.computing.spark.datasource;

import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

@RequiredArgsConstructor
public class TextFileDataSource implements SparkDataSource<String> {
    private final JavaSparkContext sparkContext;
    private  final String filePath;

    @Override
    public JavaRDD<String> getRDD(int minPartitions) {
        return sparkContext.textFile(filePath, minPartitions);
    }
}
