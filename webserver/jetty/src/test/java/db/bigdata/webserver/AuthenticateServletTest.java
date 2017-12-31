package db.bigdata.webserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import db.bigdata.webserver.commons.Utility;
import db.bigdata.webserver.models.User;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

import static com.jayway.restassured.RestAssured.given;

public class AuthenticateServletTest extends BaseServletTest {

    @Override
    String username() {
        return "girish_1";
    }

    @Test
    public void testRegisterAndLogin() throws JsonProcessingException {

        User user = new User("Girish Patel",
                username(),
                "girishpatel.bits@gmail.com",
                "pass"
        );

        given()
                .contentType("application/json")
                .body(Utility.objectToJson(user))
                .when().post("http://localhost:8080/auth/register")
                .then().statusCode(HttpServletResponse.SC_CREATED);

        given()
                .when().get("http://localhost:8080/auth/login?username=" + username() + "&password=pass")
                .then().statusCode(HttpServletResponse.SC_OK);

        given()
                .when().get("http://localhost:8080/auth/login?username=some.user&password=some.pass")
                .then().statusCode(HttpServletResponse.SC_UNAUTHORIZED);

    }

}
