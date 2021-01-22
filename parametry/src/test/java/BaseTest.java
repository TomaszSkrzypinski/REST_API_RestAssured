import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {

    private static final String KEY = "409e77b1570a2510addbb32e2b397b1b";
    private static final String TOKEN = "8402236107a065c9464cd5b1484f71cac919a1e4334e3422b0a6f2372582c87d";
    private static final String DISPLAY_NAME = "My organization name";

    protected static final String BASE_URL = "https://api.trello.com/1";
    protected static final String ORGANIZATIONS = "/organizations/";


    private static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqSpec;

    protected String id;

    @BeforeAll
    public static void beforeAll() {
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", KEY);
        reqBuilder.addQueryParam("token", TOKEN);
        reqBuilder.addQueryParam("displayName", DISPLAY_NAME);
        reqBuilder.setContentType(ContentType.JSON);
        reqBuilder.addFilter(new AllureRestAssured());

        reqSpec = reqBuilder.build();
    }

    @AfterEach
    public void deleteOrganization() {

        given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations/" + id)
                .then().statusCode(200);
    }
}
