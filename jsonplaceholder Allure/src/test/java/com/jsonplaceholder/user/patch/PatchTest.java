package com.jsonplaceholder.user.patch;

import com.jsonplaceholder.base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class PatchTest extends BaseTest {

    JSONObject user;

    @BeforeEach
    public void beforeEach() {
        user = new JSONObject();
    }

    @DisplayName("Update user with specific email ")
    @ParameterizedTest(name = "Update user with specific email: {0} ")
    @MethodSource("updateUserEmailData")
    public void updateUserEmail(String email) {

        user.put("email", email);

        Response response = given()
                .spec(reqSpec)
                .body(user.toString())
                .when()
                .patch(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("email")).isEqualTo(email);
    }

    private static Stream<Arguments> updateUserEmailData() {
        return Stream.of(
                Arguments.of("test@testemail.com"),
                Arguments.of("test@testemail.pl"),
                Arguments.of("test@testemail.eu"));
    }
}
