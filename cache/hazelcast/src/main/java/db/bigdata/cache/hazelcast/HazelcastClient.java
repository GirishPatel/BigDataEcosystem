package db.bigdata.cache.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.Getter;

@Getter
public class HazelcastClient {
    private Config config;
    private HazelcastInstance instance;

    public HazelcastClient() {
        this.config = new Config();
        init();
    }
    public HazelcastClient(Config config) {
        this.config = config;
        init();
    }
    private void init() {
        this.instance = Hazelcast.newHazelcastInstance(config);
    }

    public void shutdown() {
        this.instance.shutdown();
    }
}


