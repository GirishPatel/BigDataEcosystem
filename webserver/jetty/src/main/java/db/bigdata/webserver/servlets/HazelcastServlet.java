package db.bigdata.webserver.servlets;

import com.hazelcast.core.IMap;
import db.bigdata.cache.hazelcast.HazelcastClient;
import db.bigdata.webserver.app.ClientsMap;
import db.bigdata.webserver.commons.Client;
import db.bigdata.webserver.models.APIResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class HazelcastServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        HazelcastClient hazelcastClient = (HazelcastClient) ClientsMap.getClientObject(Client.HAZELCAST);

        String path = request.getPathInfo();

        if (path.equals("/get")) {

            String mapString = request.getParameter("map");

            IMap<String, String> testMap = hazelcastClient.getInstance().getMap("testMap");

            testMap.put("key1", "value1");
            testMap.put("key2", "value2");
        } else {
            logger.error("PATH_NOT_FOUND:/search" + path);
            APIResponse apiResponse = new APIResponse("PATH_NOT_FOUND", "WRONG_URL");
            doResponse(response, HttpServletResponse.SC_NOT_FOUND, apiResponse);
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = request.getPathInfo();

        if (path.equals("/saveAndContinue")) {

            try {
                String username = request.getSession().getAttribute(USERNAME).toString();
                String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

                // todo: do something
                // todo doSomething(response, username);

            } catch (Exception e) {
                logger.error("RATING_SAVING_FAILED:" + e.getMessage(), e);
                APIResponse apiResponse = new APIResponse("RATING_SAVING", "FAILED");
                doResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, apiResponse);
            }
        } else {
            logger.error("PATH_NOT_FOUND:/rating" + path);
            APIResponse apiResponse = new APIResponse("PATH_NOT_FOUND", "WRONG_URL");
            doResponse(response, HttpServletResponse.SC_NOT_FOUND, apiResponse);
        }

    }

}
