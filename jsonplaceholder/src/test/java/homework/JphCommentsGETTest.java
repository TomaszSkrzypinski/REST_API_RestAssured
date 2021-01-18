package homework;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JphCommentsGETTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String COMMENTS = "comments/";

   @Test
    public void getAllComments(){

        Response response = given()
                .get(BASE_URL + COMMENTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(500, json.getList("id").size());
    }

    @Test
    public void getOneCommentsWithId(){

        Response response = given()
                .pathParam("commentsId", 26)
                .get(BASE_URL + COMMENTS + "{commentsId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("26", json.get("id").toString());
        assertEquals("6", json.get("postId").toString());
        assertEquals("in deleniti sunt provident soluta ratione veniam quam praesentium", json.get("name"));
        assertEquals("Russel.Parker@kameron.io", json.get("email"));
    }
}