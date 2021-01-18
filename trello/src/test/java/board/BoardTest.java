package board;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class BoardTest extends BaseTest {

    private final String KEY = "409e77b1570a2510addbb32e2b397b1b";
    private final String TOKEN = "8402236107a065c9464cd5b1484f71cac919a1e4334e3422b0a6f2372582c87d";

    @Test
    public void createNewBoard(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Board utworzony z IntelliJ")
                .when()
                .post(BASE_URL + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("name")).isEqualTo("Board utworzony z IntelliJ");

        String boarId = json.get("id");

        given()
                .spec(reqSpec)
                .pathParam("id", boarId)
                .when()
                .delete(BASE_URL + BOARDS + "{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void createNewBoardWithEmptyBoardName(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "")
                .when()
                .post(BASE_URL + BOARDS)
                .then()
                .statusCode(400)
                .extract()
                .response();
    }

    @Test
    public void createNewBoardWithoutDefaultLists(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Board without default lists")
                .queryParam("defaultLists", false)
                .when()
                .post(BASE_URL + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("name")).isEqualTo("Board without default lists");

        String boardId = json.get("id");

        Response responseGet = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + BOARDS + boardId + "/lists")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonGet = responseGet.jsonPath();

        List<String> idList = jsonGet.getList("id");

        assertThat(idList).hasSize(0);

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + BOARDS + boardId)
                .then()
                .statusCode(200);
    }

    @Test
    public void createNewBoardWithDefaultLists(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Board with default lists")
                .queryParam("defaultLists", true)
                .when()
                .post(BASE_URL + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("name")).isEqualTo("Board with default lists");

        String boardId = json.get("id");

        Response responseGet = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + BOARDS + boardId + "/lists")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonGet = responseGet.jsonPath();

        List<String> namesList = jsonGet.getList("name");

        assertThat(namesList).hasSize(3).contains("Do zrobienia", "Zrobione", "W trakcie");

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + BOARDS + boardId)
                .then()
                .statusCode(200);
    }
}
