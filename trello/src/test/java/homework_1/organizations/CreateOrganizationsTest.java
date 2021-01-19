package homework_1.organizations;

import homework_1.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrganizationsTest extends BaseTest {

    @Test
    public void createOrganization(){

        Response response = given()
                .spec(reqSpec)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);

        id = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void createOrganizationWithoutDisplayName() {

        Response response = given()
                .queryParam("token", TOKEN)
                .queryParam("key", KEY)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(400)
                .extract()
                .response();
    }
}
