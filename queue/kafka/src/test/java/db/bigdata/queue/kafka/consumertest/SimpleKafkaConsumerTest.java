package db.bigdata.queue.kafka.consumertest;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class SimpleKafkaConsumerTest {
    private static final String GROUP_ID = "test-consumer-group";
    private static final String TOPIC_ID1 = "test-topic1";
    private static final String TOPIC_ID2 = "test-topic2";
    private static final List<String> singleTopicList = Collections.singletonList(TOPIC_ID1);
    private static final List<String> multipleTopicList = Arrays.asList(TOPIC_ID1, TOPIC_ID2);

    private SimpleKafkaConsumer simpleKafkaConsumer;

    @Before
    public void before() {
    }

    @After
    public void after() {
        if (simpleKafkaConsumer != null) simpleKafkaConsumer.close();
    }

    @Test
    public void testConsumer()  {
        simpleKafkaConsumer = new SimpleKafkaConsumer.SimpleKafkaConsumerBuilder<String, String>(GROUP_ID, singleTopicList)
                .build();
        List<String> valuesList = simpleKafkaConsumer.getNext();
        valuesList.stream().forEach(System.out::println);
        assertNotNull(simpleKafkaConsumer);
        simpleKafkaConsumer.close();
    }
}
