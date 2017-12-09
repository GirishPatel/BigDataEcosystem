package db.bigdata.queue.kafka.producertest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.bigdata.queue.kafka.producer.SimpleKafkaProducer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SimpleKafkaProducerTest {

    private static  ObjectMapper objectMapper = new ObjectMapper();
    private SimpleKafkaProducer simpleKafkaProducer;
    private static final String TOPIC_ID1 = "test-topic1";
    private static final String INVALID_TOPIC_ID = "INVALID";
    @Before
    public void before() {

    }

    @After
    public void after() {
        if(simpleKafkaProducer != null) simpleKafkaProducer.close();
    }

    @Test
    public void testSimpleKafkaProducerValidTopic() throws JsonProcessingException {
        simpleKafkaProducer =  new SimpleKafkaProducer.SimpleKafkaProducerBuilder<String, String>(TOPIC_ID1).build();
        assertNotNull(simpleKafkaProducer);
        assertNotNull(simpleKafkaProducer.getProducer());
        assertEquals(TOPIC_ID1, simpleKafkaProducer.getTopicId());
        simpleKafkaProducer.publishData(null, "hello key null");

        simpleKafkaProducer.publishData("key", "hello key not null");

        TestPojo testPojo = new TestPojo("pankaj", "flipakart");

        simpleKafkaProducer.publishData("key", objectMapper.writeValueAsString(testPojo));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestPojo {
        @JsonProperty("name")
        public String name;
        @JsonProperty("company")
        public String company;

    }

}
