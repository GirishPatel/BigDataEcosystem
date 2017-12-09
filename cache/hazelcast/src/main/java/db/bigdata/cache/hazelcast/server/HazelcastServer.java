package db.bigdata.cache.hazelcast.server;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import lombok.Getter;

@Getter
public class HazelcastServer {
    private Config config;
    private HazelcastInstance instance;

    public HazelcastServer() {
        this.config = new Config();
        init();
    }
    public HazelcastServer(Config config) {
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

