package db.bigdata.computing.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkClient {
    public static SparkConf getLocalSparkConf(String appName) {
        return getSparkConf(appName, "local");
    }
    public static SparkConf getRemoteSparkConf(String appName, String master) {
        return getSparkConf(appName, master);
    }

    public static JavaSparkContext getLocalSparkContext(SparkConf conf) {
       return new JavaSparkContext(conf);
    }

    private static SparkConf getSparkConf(String appName, String master) {
        return new SparkConf().setAppName(appName).setMaster(master);
    }
}


