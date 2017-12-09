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
        //assertEquals(hazelcastServer.getConfig().getInstanceName(), getHazelcastConfig().getInstanceName());
    }

    @Test
    public void testGetMapHazelcastServer() {
        hazelcastServer = new HazelcastServer();
        Map<String, String> map = hazelcastServer.getInstance().getMap("test");
        assertNotNull(map);
        assertEquals(0, map.size());
    }

    @Test
    public void testPutHazelcastMap() {
        hazelcastServer = new HazelcastServer();
        Map<String, String> map = hazelcastServer.getInstance().getMap("test");
        assertNotNull(map);
        map.put("test1", "test1");
        assertEquals(1, map.size());
        assertEquals("test1", map.get("test1"));
    }

    @Test
    public void testRemoveKey() {
        hazelcastServer = new HazelcastServer();
        IMap<String, String> map = hazelcastServer.getInstance().getMap("test");
        assertNotNull(map);
        map.put("test1", "test1");
        assertEquals(1, map.size());
        assertEquals("test1", map.get("test1"));
        map.remove("test1");
        assertEquals(0, map.size());
    }

    @Test
    public void testDeleteKey() {
        hazelcastServer = new HazelcastServer();
        IMap<String, String> map = hazelcastServer.getInstance().getMap("test");
        assertNotNull(map);
        map.put("test1", "test1");
        assertEquals(1, map.size());
        assertEquals("test1", map.get("test1"));
        map.delete("test1");
        assertEquals(0, map.size());
    }

    private Config getHazelcastConfig() {
        Config  config = new Config();
        config.setInstanceName("Test");
        return config;
    }
}
