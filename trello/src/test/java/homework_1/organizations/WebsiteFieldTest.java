package homework_1.organizations;

import homework_1.base.BaseTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class WebsiteFieldTest extends BaseTest {

    private String website;

    @Test
    public void usingCorrectWebsiteField(){

        website = "http://www.wp.pl";

        JsonPath json = given()
                .spec(reqSpec)
                .queryParam("website", website)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        id = json.getString("id");

        assertThat(json.getString("website")).isEqualTo(website);
    }

    @Test
    public void WebsiteFieldWithoutHttp() {

        website = "www.wp.pl";

        JsonPath json = given()
                .spec(reqSpec)
                .queryParam("website", website)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        id = json.getString("id");

        assertThat(json.getString("website")).startsWith("http://");
    }

    @Test
    public void WebsiteFieldWithColonSlashSlash(){
        website = "t://www.wp.pl";

        JsonPath json = given()
                .spec(reqSpec)
                .queryParam("website", website)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        id = json.getString("id");

        //ten test nie przechodzi, trello nie poprawia automatycznie adresu zawierającego ://
        assertThat(json.getString("website")).startsWith("http://");
    }
}
