package db.bigdata.webserver.app;

import db.bigdata.cache.hazelcast.HazelcastClient;
import db.bigdata.webserver.commons.Client;

public class Bootstrap {

    public static void init(String[] clients) throws Exception {

        Client[] dbClients = new Client[clients.length];
        for (int i = 0; i < clients.length; i++) {
            dbClients[i] = Client.valueOf(clients[i]);
        }

        ClientsMap clientsMap = ClientsMap.getInstance();
        for (Client client : dbClients) {
            switch (client) {
                case HAZELCAST:
                    clientsMap.addClient(Client.HAZELCAST, new HazelcastClient());
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

    }

}
