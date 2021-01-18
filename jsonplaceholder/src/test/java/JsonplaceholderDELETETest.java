import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaceholderDELETETest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String USERS = "users/";

    @Test
    public void jsonplaceholderDeleteUser(){

        Response userId = given()
                .when()
                .pathParam("userId", 1)
                .delete(BASE_URL + USERS + "{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }
}
