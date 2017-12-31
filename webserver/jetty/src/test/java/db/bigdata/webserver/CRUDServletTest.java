package db.bigdata.webserver;

public class CRUDServletTest extends BaseServletTest {

    @Override
    String username() {
        return "girish_2";
    }

//    @Test
//    public void testSearch() throws IOException {
//
//        SearchResult[] searchResults = getTestSearchResults();
//
//        for (SearchResult searchResult : searchResults) {
//            SearchDao.createSearchResult(searchResult);
//        }
//
//        User user = new User(
//                "Girish Patel",
//                username(),
//                "girishpatel.bits@gmail.com",
//                "pass"
//        );
//
//        SessionFilter sessionFilter = new SessionFilter();
//
//        given()
//                .contentType("application/json")
//                .body(Utility.objectToJson(user))
//                .when().post("http://localhost:8080/auth/register")
//                .then().statusCode(HttpServletResponse.SC_CREATED);
//
//        given()
//                .filter(sessionFilter)
//                .when().get("http://localhost:8080/auth/login?username=" + username() + "&password=pass")
//                .then().statusCode(HttpServletResponse.SC_OK);
//
//        given()
//                .filter(sessionFilter)
//                .when().get("http://localhost:8080/search/data")
//                .then()
//                .statusCode(HttpServletResponse.SC_OK)
//                .body("keyword", equalTo("na ja"))
//                .body("type", equalTo("song"))
//                .body("searchResults.size()", equalTo(5));
//
//        given()
//                .filter(sessionFilter)
//                .when().get("http://localhost:8080/search/data")
//                .then()
//                .statusCode(HttpServletResponse.SC_OK)
//                .body("keyword", equalTo("closer"))
//                .body("type", equalTo("song"))
//                .body("searchResults.size()", equalTo(0));
//
//        given()
//                .filter(sessionFilter)
//                .when().get("http://localhost:8080/search/data")
//                .then()
//                .statusCode(HttpServletResponse.SC_OK)
//                .body("keyword", equalTo("abcd"))
//                .body("type", equalTo("album"))
//                .body("searchResults.size()", equalTo(0));
//    }

}

