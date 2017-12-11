package db.bigdata.queue.kafka.producer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

@Getter
@Slf4j
public class SimpleKafkaProducer<K,V> {
    private Producer producer;

    private SimpleKafkaProducer(Properties properties) {
        this.producer = new KafkaProducer<K, V>(properties);
    }
    public void publishData(String topicId, K key, V  data) {
        RecordPublishCallback localCallback = new RecordPublishCallback();
        ProducerRecord<K, V> producerRecord = new ProducerRecord<>(topicId, key, data);
        producer.send(producerRecord, localCallback);
    }
    public void close() {
        this.producer.close();
    }

    private static class RecordPublishCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e == null) {
                log.info(String.format("sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset()));
            } else {
                log.error("Error while producing message to topic :" + recordMetadata);
                e.printStackTrace();
            }
        }
    }


    public static class SimpleKafkaProducerBuilder<K,V>{
        private Properties properties;

        public SimpleKafkaProducerBuilder() {
            this.properties = getDefaultProducerProperty();
        }

        public SimpleKafkaProducerBuilder setBootStrapServerConfig(String bootStrapServerConfig) {
            this.properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServerConfig);
            return this;
        }

        public SimpleKafkaProducerBuilder setAckConfig(String ackConfig) {
            this.properties.put(ProducerConfig.ACKS_CONFIG, ackConfig);
            return this;
        }

        public SimpleKafkaProducerBuilder setRetryConfig(int retryConfig) {
            this.properties.put(ProducerConfig.RETRIES_CONFIG, retryConfig);
            return this;
        }
        public SimpleKafkaProducerBuilder setKeySerializer(String serializer) {
            this.properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializer);
            return this;
        }
        public SimpleKafkaProducerBuilder setValueSerializer(String serializer) {
            this.properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer);
            return this;
        }
        public SimpleKafkaProducer build() {
            return new SimpleKafkaProducer(properties);
        }



        private Properties getDefaultProducerProperty() {
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            properties.put(ProducerConfig.ACKS_CONFIG, "all");
            properties.put(ProducerConfig.RETRIES_CONFIG, 0);
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

            return properties;

        }
    }
}
