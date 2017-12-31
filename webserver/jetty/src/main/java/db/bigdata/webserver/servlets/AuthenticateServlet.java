package db.bigdata.webserver.servlets;

import db.bigdata.webserver.commons.Utility;
import db.bigdata.webserver.models.APIResponse;
import db.bigdata.webserver.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class AuthenticateServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        String path = request.getPathInfo();

        if (path.equals("/login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            logger.debug("Login Received");

            try {
                User user = null; // todo: getUserData(username, password)

                if (user != null) {
                    request.getSession().setAttribute(USERNAME, username);
                    APIResponse apiResponse = new APIResponse("LOGGED_IN", "SUCCESS");
                    doResponse(response, HttpServletResponse.SC_OK, apiResponse);
                } else {
                    request.getSession().setAttribute(USERNAME, username);
                    APIResponse apiResponse = new APIResponse("LOGGED_IN", "USER_AND_PASSWORD_MISMATCH:" + username);
                    doResponse(response, HttpServletResponse.SC_UNAUTHORIZED, apiResponse);
                }
            } catch (Exception e) {
                logger.error("AUTHENTICATION_FAILED:" + e.getMessage(), e);
                APIResponse apiResponse = new APIResponse("LOGGED_IN", "FAILED:" + username);
                doResponse(response, HttpServletResponse.SC_UNAUTHORIZED, apiResponse);
            }

        } else {
            logger.error("PATH_NOT_FOUND:/auth" + path);
            APIResponse apiResponse = new APIResponse("PATH_NOT_FOUND", "WRONG_URL");
            doResponse(response, HttpServletResponse.SC_NOT_FOUND, apiResponse);
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = request.getPathInfo();

        if (path.equals("/register")) {

            logger.debug("Register Received");

            User user;
            try {
                String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                user = Utility.objectMapper.readValue(data, User.class);
                user.hashPassword();
            } catch (Exception e) {
                logger.error("REGISTER_DESERIALIZATION_FAILED:" + e.getMessage(), e);
                APIResponse apiResponse = new APIResponse("REGISTER", "BAD_REQUEST");
                doResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
                return;
            }

            boolean isCreated = false; // todo: createUser(user);

            if (isCreated) {
                APIResponse apiResponse = new APIResponse("REGISTER", "SUCCESS");
                doResponse(response, HttpServletResponse.SC_CREATED, apiResponse);
            } else {
                APIResponse apiResponse = new APIResponse("REGISTER", "ALREADY_EXIST");
                doResponse(response, HttpServletResponse.SC_CONFLICT, apiResponse);
            }

        } else {
            logger.error("PATH_NOT_FOUND:/auth" + path);
            APIResponse apiResponse = new APIResponse("PATH_NOT_FOUND", "WRONG_URL");
            doResponse(response, HttpServletResponse.SC_NOT_FOUND, apiResponse);
        }

    }

}
