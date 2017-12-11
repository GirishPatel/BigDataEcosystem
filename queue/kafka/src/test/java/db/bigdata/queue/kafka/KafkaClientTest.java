package db.bigdata.queue.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.bigdata.queue.kafka.consumer.SimpleKafkaConsumer;
import db.bigdata.queue.kafka.producer.SimpleKafkaProducer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class KafkaClientTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String GROUP_ID = "test-consumer-group";
    private static final String TOPIC_ID1 = "test-topic1";
    private static final String TOPIC_ID2 = "test-topic2";
    private static final List<String> singleTopicList = Collections.singletonList(TOPIC_ID1);
    private static final List<String> multipleTopicList = Arrays.asList(TOPIC_ID1, TOPIC_ID2);

    private SimpleKafkaConsumer simpleKafkaConsumer;
    private SimpleKafkaProducer simpleKafkaProducer;

    @Before
    public void before() {
    }

    @After
    public void after() {
        if (simpleKafkaConsumer != null) simpleKafkaConsumer.close();
        if (simpleKafkaProducer != null) simpleKafkaProducer.close();
    }

    @Test
    public void testConsumerProducerSingleTopic() throws InterruptedException {
        simpleKafkaConsumer = new SimpleKafkaConsumer.SimpleKafkaConsumerBuilder<String, String>(GROUP_ID, singleTopicList)
                .build();
        simpleKafkaProducer = new SimpleKafkaProducer.SimpleKafkaProducerBuilder<String, String>()
                .build();

        assertNotNull(simpleKafkaConsumer);
        assertNotNull(simpleKafkaConsumer.getConsumer());

        assertNotNull(simpleKafkaProducer);
        assertNotNull(simpleKafkaProducer.getProducer());

        // consume the queue if the there is some data to consume
        while(simpleKafkaConsumer.getNext(100).size() != 0) ;

        simpleKafkaProducer.publishData(TOPIC_ID1, "key", "hello");
        simpleKafkaProducer.publishData(TOPIC_ID1, "key", "hello");


        Thread.sleep(100);
        List<String> values = simpleKafkaConsumer.getNext(1000);

        assertEquals(2, values.size());
        for(String value : values) {
            assertEquals("hello", value);
        }
    }

    @Test
    public void testConsumerProducerMultipleTopic() throws InterruptedException {
        simpleKafkaConsumer = new SimpleKafkaConsumer.SimpleKafkaConsumerBuilder<String, String>(GROUP_ID, multipleTopicList)
                .build();
        simpleKafkaProducer = new SimpleKafkaProducer.SimpleKafkaProducerBuilder<String, String>()
                .build();

        assertNotNull(simpleKafkaConsumer);
        assertNotNull(simpleKafkaConsumer.getConsumer());

        assertNotNull(simpleKafkaProducer);
        assertNotNull(simpleKafkaProducer.getProducer());


        // consume the queue if the there is some data to consume
        while(simpleKafkaConsumer.getNext(1000).size() != 0);

        simpleKafkaProducer.publishData(TOPIC_ID1, null, "hello");
        simpleKafkaProducer.publishData(TOPIC_ID1, "key", "hello");
        simpleKafkaProducer.publishData(TOPIC_ID2, null, "hello");
        simpleKafkaProducer.publishData(TOPIC_ID2, "key", "hello");


        Thread.sleep(100);
        List<String> values = new ArrayList<>();

        while(true){
            List<String> valueList = simpleKafkaConsumer.getNext(100);
            values.addAll(valueList);
            if(valueList.size() == 0) break;
        }

        assertEquals(4, values.size());
        for(String value : values) {
            assertEquals("hello", value);
        }
    }

    @Test
    public void testCustomSerializationAndDeserialization() throws InterruptedException {
        simpleKafkaConsumer = new SimpleKafkaConsumer.SimpleKafkaConsumerBuilder<String, Person>(GROUP_ID, multipleTopicList)
                .setValueDeserializer(Person.PersonDeserializer.class.getName())
                .build();
        simpleKafkaProducer = new SimpleKafkaProducer.SimpleKafkaProducerBuilder<String, Person>()
                .setValueSerializer(Person.PersonSerializer.class.getName())
                .build();

        // consume the queue if the there is some data to consume
        while(simpleKafkaConsumer.getNext(1000).size() != 0);

        simpleKafkaProducer.publishData(TOPIC_ID1, null, new Person("pankaj", 21));
        simpleKafkaProducer.publishData(TOPIC_ID1, "key",  new Person("pankaj", 21));
        simpleKafkaProducer.publishData(TOPIC_ID2, null,  new Person("pankaj", 21));
        simpleKafkaProducer.publishData(TOPIC_ID2, "key",  new Person("pankaj", 21));

        Thread.sleep(100);

        List<Person> personList = new ArrayList<>();

        while(true){
            List<Person> valueList = simpleKafkaConsumer.getNext(100);
            personList.addAll(valueList);
            if(valueList.size() == 0) break;
        }

        assertEquals(4, personList.size());
        for(Person person : personList) {
            assertEquals("pankaj", person.getName());
        }

    }

}


