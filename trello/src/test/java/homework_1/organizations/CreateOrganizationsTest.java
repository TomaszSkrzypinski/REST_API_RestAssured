package homework_1.organizations;

import homework_1.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrganizationsTest extends BaseTest {

    private String testDisplayName = "organization display name";

    @Test
    public void createOrganizationWithDisplayName(){

        JsonPath json = given()
                .queryParam("token", TOKEN)
                .queryParam("key", KEY)
                .queryParam("displayName", testDisplayName)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(testDisplayName);

        id = json.getString("id");
    }

    @Test
    public void createOrganizationWithoutDisplayName() {

        given()
                .queryParam("token", TOKEN)
                .queryParam("key", KEY)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(400);

        //Specjalnie tworzę organizację, żeby metoda deleteOrganization z AfterEach miała co usunąć i nie zgłaszała błędu
        JsonPath json = given()
                .spec(reqSpec)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        id = json.getString("id");
    }
}
