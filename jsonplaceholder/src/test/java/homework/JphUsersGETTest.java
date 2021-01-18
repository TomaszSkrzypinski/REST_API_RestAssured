package homework;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JphUsersGETTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String USERS = "users";

    @Test
    public void checkUsersEmails() {

        Response response = given()
                .when()
                .get(BASE_URL + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List <String> emails = json.getList("email");

        List <String> filteredEmails = emails.stream()
                .filter(email -> email.endsWith(".pl"))
                .collect(Collectors.toList());

        assertEquals(0, filteredEmails.size());
    }
}