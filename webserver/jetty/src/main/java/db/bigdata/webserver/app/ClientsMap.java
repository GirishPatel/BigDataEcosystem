package db.bigdata.webserver.app;

import db.bigdata.webserver.commons.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientsMap {
    private static ClientsMap ourInstance = new ClientsMap();
    private Map<Client, Object> clientMap = new HashMap<Client, Object>();

    public static ClientsMap getInstance() {
        return ourInstance;
    }

    private ClientsMap() {}

    public static void addClient(Client client, Object clientObject) throws Exception {
        if (ourInstance.clientMap.containsKey(client)) {
            throw new Exception("CLIENT_ALREADY_EXIST:" + client);
        } else {
            ourInstance.clientMap.put(client, clientObject);
        }
    }

    public static Object getClientObject(Client client) {
        return ourInstance.clientMap.get(client);
    }

}
