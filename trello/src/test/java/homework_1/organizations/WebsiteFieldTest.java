package homework_1.organizations;

import homework_1.base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class WebsiteFieldTest extends BaseTest {

    @Test
    public void usingCorrectWebsiteField(){

        website = "http://www.wp.pl";

        Response response = given()
                .spec(reqSpec)
                .queryParam("website", website)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("id");

        assertThat(json.getString("website")).isEqualTo(website);

        System.out.println(json.getString("website"));

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void WebsiteFieldWithoutHttp() {

        website = "www.wp.pl";

        Response response = given()
                .spec(reqSpec)
                .queryParam("website", website)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("id");

        assertThat(json.getString("website")).startsWith("http://");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void WebsiteFieldWithColonSlashSlash(){
        website = "t://www.wp.pl";

        Response response = given()
                .spec(reqSpec)
                .queryParam("website", website)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);

        //ten test nie przechodzi, trello nie poprawia automatycznie adresu zawierającego ://
        assertThat(json.getString("website")).startsWith("http://");
    }
}
