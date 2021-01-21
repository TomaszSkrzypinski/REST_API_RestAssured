package com.jsonplaceholder.user.get;

import com.jsonplaceholder.base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTest extends BaseTest {

    @Test
    public void readAllUsers() {

        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        assertEquals(10, names.size());
    }

    @Test
    public void readOneUser() {
        Response response = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));

    }

    @Test
    public void readOneUserWithPathVariable() {

        Response response = given()
                .spec(reqSpec)
                .pathParam("userId", 1)
                .when()
                .get(BASE_URL + "/" + USERS + "/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));
    }

    @Test
    public void readUsersWithQueryParams() {

        Response response = given()
                .spec(reqSpec)
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.getList("name").get(0));
        assertEquals("Bret", json.getList("username").get(0));
        assertEquals("Sincere@april.biz", json.getList("email").get(0));
        assertEquals("Kulas Light", json.getList("address.street").get(0));
    }
}
