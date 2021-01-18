package homework;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JphCommentsPOSTTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String COMMENTS = "comments/";

    private static Faker faker;
    private String fakePostId;
    private String fakeEmail;
    private String fakeBody;
    private String fakeName;

    @BeforeAll
    public static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach(){
        fakePostId = String.valueOf(faker.number().numberBetween(1, 100));
        fakeEmail = faker.internet().emailAddress();
        fakeBody = faker.lorem().sentence();
        fakeName = faker.name().username();
    }

    @Test
    public void createComment(){

        JSONObject body = new JSONObject();
        body.put("postId", fakePostId);
        body.put("email", fakeEmail);
        body.put("body", fakeBody);
        body.put("fakeName", fakeName);

        Response response = given()
                .contentType("application/json")
                .body(body.toString())
                .post(BASE_URL + COMMENTS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(fakePostId, json.get("postId"));
        Assertions.assertEquals(fakeEmail, json.get("email"));
        Assertions.assertEquals(fakeBody, json.get("body"));
    }
}