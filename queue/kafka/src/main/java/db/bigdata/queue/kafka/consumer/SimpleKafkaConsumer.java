package db.bigdata.queue.kafka.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Getter
@Slf4j
public class SimpleKafkaConsumer<K, V> {
    private static final long defaultTimeOutMS = 10;
    private String groupId;
    private List<String> topics;
    private Consumer<K, V> consumer;


    private SimpleKafkaConsumer(Properties properties, String groupId, List<String> topics) {
        this.groupId = groupId;
        this.topics = topics;
        consumer = new KafkaConsumer<K, V>(properties);
        consumer.subscribe(topics);
    }

    public List<V>  getNext(long timeOut) {
        ConsumerRecords<K, V> consumerRecords = consumer.poll(timeOut);
        List<V> recordValueList = new ArrayList<>();
        consumerRecords.forEach(record -> recordValueList.add(record.value()));
        consumer.commitAsync(new DefaultOffsetCommitCallback());
        return recordValueList;
    }
    public List<V> getNext() {
        return getNext(defaultTimeOutMS);
    }

    public void close() {
        this.consumer.close();
    }

    private class DefaultOffsetCommitCallback implements OffsetCommitCallback {
        @Override
        public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
            if (exception == null) {
                log.info("Offset commit with offsets {} failed", offsets);
            } else {
                log.error("Offset commit with offsets {} failed", offsets, exception);
            }
        }
    }

    public static class SimpleKafkaConsumerBuilder<K,V>{
        private String groupId;
        private List<String> topics;
        private Properties properties;
        public SimpleKafkaConsumerBuilder(String groupId, List<String> topics){
            this.groupId = groupId;
            this.topics = topics;
            this.properties = getDefaultConsumerProperty();
        }

        public SimpleKafkaConsumerBuilder setBootStrapServers(String bootStrapServers) {
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
            return this;
        }
        public SimpleKafkaConsumerBuilder setEnableAutoCommit(boolean enableAutoCommit) {
            properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
            return this;
        }
        public SimpleKafkaConsumerBuilder setAutoCommitIntervalMS(long autoCommitIntervalMS) {
            properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitIntervalMS);
            return this;
        }
        public SimpleKafkaConsumerBuilder setSessionTimeOutMS(long sessionTimeOutMS) {
            properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeOutMS);
            return this;
        }
        public SimpleKafkaConsumerBuilder setAutoOffsetReset(String autoOffsetReset) {
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
            return this;
        }
        public SimpleKafkaConsumerBuilder setKeySerializer(String serializer) {
            this.properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, serializer);
            return this;
        }
        public SimpleKafkaConsumerBuilder setValueSerializer(String serializer) {
            this.properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, serializer);
            return this;
        }
        public SimpleKafkaConsumer build() {
            return new SimpleKafkaConsumer<K,V>(properties, groupId, topics);
        }

        private Properties getDefaultConsumerProperty() {
            Properties properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
            properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
            properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            return properties;
        }
    }


}
