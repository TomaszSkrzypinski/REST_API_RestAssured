package homework_2.e2e;

import homework_2.base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoveCardBetweenLists extends BaseTest {

    protected static String boardId;
    protected static String firstListId;
    protected static String secondListId;
    protected static String cardId;

    @Test
    @Order(1)
    public void createBoard(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "My E2E list")
                .queryParam("defaultLists", false)
                .when()
                .post(BASE_URL + BOARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        boardId = json.getString("id");

        assertThat(json.getString("name")).isEqualTo("My E2E list");
    }

    @Test
    @Order(2)
    public void createFirstList(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("idBoard", boardId)
                .queryParam("name", "First list")
                .when()
                .post(BASE_URL + LISTS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        firstListId = json.getString("id");

        assertThat(json.getString("name")).isEqualTo("First list");
    }

    @Test
    @Order(3)
    public void createSecondList(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("idBoard", boardId)
                .queryParam("name", "Second list")
                .when()
                .post(BASE_URL + LISTS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        secondListId = json.getString("id");

        assertThat(json.getString("name")).isEqualTo("Second list");
    }

    @Test
    @Order(4)
    public void createCard(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("idList", firstListId)
                .queryParam("name", "My card")
                .when()
                .post(BASE_URL + CARDS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        cardId = json.getString("id");

        assertThat(json.getString("name")).isEqualTo("My card");
    }

    @Test
    @Order(5)
    public void movedCardToSecondList(){

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "My moved card")
                .queryParam("idList", secondListId)
                .when()
                .put(BASE_URL + CARDS + cardId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("name")).isEqualTo("My moved card");
        assertThat(json.getString("idList")).isEqualTo(secondListId);
    }

    @Test
    @Order(6)
    public void deleteList(){

        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + BOARDS + boardId)
                .then()
                .statusCode(200);
    }
}
