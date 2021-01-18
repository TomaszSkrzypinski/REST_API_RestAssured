import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPUTPATCHTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String USERS = "users/";

    private static Faker faker;
    private String fakeName;
    private String fakeUserName;
    private String fakePhoneNumber;
    private String fakeEmail;
    private String fakeWWW;

    @BeforeAll
    public static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach() {
        fakeName = faker.name().fullName();
        fakeUserName = faker.name().username();
        fakePhoneNumber = faker.phoneNumber().phoneNumber();
        fakeEmail = faker.internet().emailAddress();
        fakeWWW = faker.internet().url();
    }

    @Test
    public void jsonplaceholderUpdateUserPUT(){

        JSONObject user = new JSONObject();
        user.put("name", fakeName);
        user.put("username", fakeUserName);
        user.put("email", fakeEmail);
        user.put("phone", fakePhoneNumber);
        user.put("website", fakeWWW);

        JSONObject address = new JSONObject();
        address.put("street", "Kulas Light");
        address.put("suite", "Apt. 556");
        address.put("city", "Gwenborough");
        address.put("zipcode", "92998-3874");

        JSONObject geo = new JSONObject();
        geo.put("lat", "-37.3159");
        geo.put("lng", "81.1496");

        address.put("geo", geo);

        JSONObject company = new JSONObject();
        company.put("name", "Romaguera-Crona");
        company.put("catchPhrase", "Multi-layered client-server neural-net");
        company.put("bs", "harness real-time e-markets");

        user.put("company", company);

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .pathParam("userId", 1)
                .when()
                .put(BASE_URL + USERS + "{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakeName, json.get("name"));
        assertEquals(fakeUserName, json.get("username"));
        assertEquals(fakeEmail, json.get("email"));
    }

    @Test
    public void jsonplaceholderUpdateUserPATCH(){

        JSONObject userDetails = new JSONObject();
        userDetails.put("name", fakeName);
        userDetails.put("username", fakeUserName);
        userDetails.put("email", fakeEmail);

        Response response = given()
                .contentType("application/json")
                .body(userDetails.toString())
                .pathParam("userId", 1)
                .when()
                .put(BASE_URL + USERS + "{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakeName, json.get("name"));
        assertEquals(fakeUserName, json.get("username"));
        assertEquals(fakeEmail, json.get("email"));
    }
}
