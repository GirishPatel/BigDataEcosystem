package db.bigdata.webserver.servlets;

import db.bigdata.webserver.models.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {

    final String USERNAME = "username";

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    protected void doResponse(HttpServletResponse response, int status, APIResponse apiResponse) {
        try {
            String responseText = apiResponse.toString();
            if (status >= 400) {
                logger.error("HTTP_ERROR:" + responseText);
            } else if(status >= 300) {
                logger.info("HTTP_WARN:" + responseText);
            }
            response.setStatus(status);
            response.setContentType("application/json");
            response.setContentLength(responseText.length());
            response.getWriter().println(responseText);
        } catch (IOException e){
            logger.error("RESPONSE_WRITER_FAILED:" + e.getMessage(), e);
        }
    }

}
