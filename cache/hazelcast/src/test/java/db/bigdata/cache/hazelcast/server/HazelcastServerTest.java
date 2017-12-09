package db.bigdata.cache.hazelcast.server;

import com.hazelcast.config.Config;
import com.hazelcast.core.IMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class HazelcastServerTest {
    private HazelcastServer hazelcastServer;

    @Before
    public void before() {
        hazelcastServer = null;
    }
    @After
    public void tearDown() {
        this.hazelcastServer.shutdown();
    }

    @Test
    public void testNewHazelcastServerWithoutConfig() {
        hazelcastServer = new HazelcastServer();
        assertNotNull(hazelcastServer);
        assertNotNull(hazelcastServer.getConfig());
        assertNotNull(hazelcastServer.getInstance());
    }

    @Test
    public void testNewHazelcastServerWithConfig() {
        hazelcastServer = new HazelcastServer(getHazelcastConfig());
        assertNotNull(hazelcastServer);
        assertNotNull(hazelcastServer.getConfig());
        assertNotNull(hazelcastServer.getInstance());
        System.out.println(hazelcastServer.getConfig().getInstanceName());
        System.out.println(getHazelcastConfig().getInstanceName());
        assertEquals(hazelcastServer.getConfig().getInstanceName(), getHazelcastConfig().getInstanceName());
    }

    @Test
    public void testHazelcastMap() {
        hazelcastServer = new HazelcastServer();
        IMap<String, String> testMap = hazelcastServer.getInstance().getMap("testMap");
        assertNotNull(testMap);
        assertEquals(0, testMap.size());
        testMap.put("key1", "value1");
        testMap.put("key2", "value2");
        assertEquals(2, testMap.size());
        assertEquals("value1", testMap.get("key1"));

        testMap.remove("key1");
        assertEquals(1, testMap.size());

        testMap.delete("key2");
        assertEquals(0, testMap.size());
    }

    private Config getHazelcastConfig() {
        Config  config = new Config();
        config.setInstanceName("Test");
        return config;
    }
}
