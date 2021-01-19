package homework_1.organizations;

import homework_1.base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class NameFieldTest extends BaseTest {

    @Test
    public void createOrganizationWithCorrectName(){

        name = "_a1_a1_a1";

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json.getString("name")).isEqualTo(name);

        id = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void createOrganizationsWithThisSameName(){

        name = "_a1_a1_a1";

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json.getString("name")).isEqualTo(name);

        id = json.getString("id");

        Response response2 = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json2 = response2.jsonPath();

        id2 = json2.getString("id");

        assertThat(json2.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json2.getString("name")).isEqualTo(name + "1");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id2)
                .then()
                .statusCode(200);
    }

    @Test
    public void createOrganizationWithToShortName(){

        name = "ab";

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json.getString("name")).isNotEqualTo(name);

        id = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);
    }

    @Test
    public void createOrganizationWithAllowedSingsInName(){

        name = "a1_a1_a1_Ba?a/a-a";

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", name)
                .when()
                .post(BASE_URL + ORG)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(DISPLAY_NAME);
        assertThat(json.getString("name")).isEqualTo("a1_a1_a1_baaaa");

        id = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG + id)
                .then()
                .statusCode(200);
    }

}
