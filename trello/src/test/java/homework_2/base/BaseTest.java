package homework_2.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static final String BASE_URL = "https://api.trello.com/1/";
    protected static final String BOARDS = "boards/";
    protected static final String LISTS = "lists/";
    protected static final String CARDS = "cards/";

    protected static final String KEY = "409e77b1570a2510addbb32e2b397b1b";
    protected static final String TOKEN = "8402236107a065c9464cd5b1484f71cac919a1e4334e3422b0a6f2372582c87d";

    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqSpec;

    @BeforeAll
    public static void beforeAll(){
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", KEY);
        reqBuilder.addQueryParam("token", TOKEN);
        reqBuilder.setContentType(ContentType.JSON);

        reqSpec = reqBuilder.build();
    }
}
