package homework_1.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest{

    protected static final String BASE_URL = "https://api.trello.com/1/";
    protected static final String ORG = "organizations/";

    protected static final String DISPLAY_NAME = "my organizations name";
    protected static final String KEY = "409e77b1570a2510addbb32e2b397b1b";
    protected static final String TOKEN = "8402236107a065c9464cd5b1484f71cac919a1e4334e3422b0a6f2372582c87d";

    //Zostawiam to pole ponieważ korzystam z niego w tej klasie w AfterEach
    protected static String id;

    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqSpec;

    @BeforeAll
    public static void beforeAll(){
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", KEY);
        reqBuilder.addQueryParam("token", TOKEN);
        reqBuilder.addQueryParam("displayName", DISPLAY_NAME);
        reqBuilder.setContentType(ContentType.JSON);
        reqBuilder.addFilter(new AllureRestAssured());

        reqSpec = reqBuilder.build();
    }

    @AfterEach
    public void deleteOrganization(){

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);
    }
}
