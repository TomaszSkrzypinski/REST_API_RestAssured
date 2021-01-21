package homework_1.organizations;

import homework_1.base.BaseTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class NameFieldTest extends BaseTest {

    private String name;
    protected static String secondOrganizationId;

    @Test
    public void createOrganizationWithCorrectName(){

        name = "_a1_a1_a1";

        JsonPath json = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json.getString("name")).isEqualTo(name);

        id = json.getString("id");
    }

    @Test
    public void createOrganizationsWithThisSameName(){

        name = "_a1_a1_a1";

        JsonPath json = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json.getString("name")).isEqualTo(name);

        id = json.getString("id");

        JsonPath json2 = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        secondOrganizationId = json2.getString("id");

        assertThat(json2.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json2.getString("name")).isEqualTo(name + "1");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + secondOrganizationId)
                .then()
                .statusCode(200);
    }

    @Test
    public void createOrganizationWithToShortName(){

        name = "ab";

        JsonPath json = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json.getString("name")).isNotEqualTo(name);

        id = json.getString("id");
    }

    @Test
    public void createOrganizationWithAllowedSingsInName(){

        name = "a1_a1_a1_Ba?a/a-a";

        JsonPath json = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json.getString("name")).isEqualTo("a1_a1_a1_baaaa");

        id = json.getString("id");
    }
}