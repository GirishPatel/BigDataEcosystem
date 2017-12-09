package db.bigdata.queue.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Person {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private String name;
    private int age;

    public static class PersonSerializer implements Serializer<Person> {
        private boolean isKey;

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            this.isKey = isKey;
        }

        @Override
        public byte[] serialize(String topic, Person data) {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new byte[0];
        }

        @Override
        public void close() {

        }
    }

    public static class PersonDeserializer implements Deserializer<Person> {
        private boolean isKey;

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            this.isKey = isKey;
        }

        @Override
        public Person deserialize(String topic, byte[] data) {
            if(data == null) return null;
            try {
                return objectMapper.readValue(data, Person.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void close() {

        }
    }
}
