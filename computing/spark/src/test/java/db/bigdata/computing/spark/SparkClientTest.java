package db.bigdata.computing.spark;

import db.bigdata.computing.spark.datasource.DataSourceProviderFactory;
import db.bigdata.computing.spark.datasource.SparkDataSource;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple2;

import java.util.*;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class SparkClientTest {

    @Before
    public void before() {

    }

    @After
    public void after() {

    }

    @Test
    public void testFileDataSource() {
        final Pattern SPACE = Pattern.compile(" ");
        String filePath = "testFile.txt";
        String outFilePath = "testOutFile";
        SparkConf sparkConf = SparkClient.getLocalSparkConf("test.app");
        JavaSparkContext sparkContext = SparkClient.getLocalSparkContext(sparkConf);
        SparkDataSource sparkDataSource = DataSourceProviderFactory.getDataSource(sparkContext, SparkDataSource.SparkDataSourceType.TEXT_FILE_DATA_SOURCE, Optional.of(filePath));
        
        JavaRDD<String> lineRdd = sparkDataSource.getRDD(1);
        JavaRDD<String> words = lineRdd.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());
        JavaPairRDD<String, Integer> wordCountPair = words.mapToPair(v-> new Tuple2<>(v,1)).reduceByKey((a,b) -> a+b);

        // output can be saved in file but make sure file path should not exist.
        wordCountPair.saveAsTextFile(outFilePath);

        Map<String, Integer>  map = wordCountPair.collectAsMap();
        assertEquals(Integer.valueOf(2),  map.get("hello"));

        for(Map.Entry<String,Integer> entry: map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());

        }
    }


}


