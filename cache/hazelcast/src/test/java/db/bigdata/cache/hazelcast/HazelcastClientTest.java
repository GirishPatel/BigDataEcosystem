package db.bigdata.cache.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.IMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class HazelcastClientTest {
    private HazelcastClient hazelcastClient;

    @Before
    public void before() {
        hazelcastClient = null;
    }
    @After
    public void tearDown() {
        this.hazelcastClient.shutdown();
    }

    @Test
    public void testNewHazelcastClientWithoutConfig() {
        hazelcastClient = new HazelcastClient();
        assertNotNull(hazelcastClient);
        assertNotNull(hazelcastClient.getConfig());
        assertNotNull(hazelcastClient.getInstance());
    }

    @Test
    public void testNewHazelcastClientWithConfig() {
        hazelcastClient = new HazelcastClient(getHazelcastConfig());
        assertNotNull(hazelcastClient);
        assertNotNull(hazelcastClient.getConfig());
        assertNotNull(hazelcastClient.getInstance());
        System.out.println(hazelcastClient.getConfig().getInstanceName());
        System.out.println(getHazelcastConfig().getInstanceName());
        assertEquals(hazelcastClient.getConfig().getInstanceName(), getHazelcastConfig().getInstanceName());
    }

    @Test
    public void testHazelcastMap() {
        hazelcastClient = new HazelcastClient();
        IMap<String, String> testMap = hazelcastClient.getInstance().getMap("testMap");
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
