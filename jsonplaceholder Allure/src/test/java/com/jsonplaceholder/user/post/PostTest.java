package com.jsonplaceholder.user.post;

import com.jsonplaceholder.base.BaseTest;
import com.jsonplaceholder.model.Address;
import com.jsonplaceholder.model.Company;
import com.jsonplaceholder.model.Geo;
import com.jsonplaceholder.model.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class PostTest extends BaseTest {

    private User user;
    private Address address;
    private Geo geo;
    private Company company;

    @BeforeEach
    public void beforeEach() {

        geo = new Geo();
        geo.setLat("-37.3159");
        geo.setLng("81.1496");

        address = new Address();
        address.setStreet("Sezamkowa");
        address.setSuite("Apt 1");
        address.setCity("New York");
        address.setZipcode("123123");
        address.setGeo(geo);

        company = new Company();
        company.setName("Company name");
        company.setCatchPhrase("Multi-layered client-server neural-net");
        company.setBs("harness real-time e-markets");

        user = new User();
        user.setName("Bartosz Testowy");
        user.setUsername("Bartek");
        user.setEmail("bartek@akademiaqa.pl");
        user.setPhone("123123123");
        user.setWebsite("www.akademiaQa.pl");
        user.setAddress(address);
        address.setGeo(geo);
    }

    @Test()
    public void createNewUser() {

        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post(BASE_URL + "/" + USERS)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(user.getName());
    }

    @DisplayName("Create user with specific email ")
    @ParameterizedTest(name = "Create user with specific email: {0} ")
    @MethodSource("createUserEmailData")
    public void createUserWithSpecificEmail(String email) {

        user.setEmail(email);

        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post(BASE_URL + "/" + USERS)
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(user.getName());
        Assertions.assertThat(json.getString("email")).isEqualTo(user.getEmail());
    }


    private static Stream<Arguments> createUserEmailData() {
        return Stream.of(
                Arguments.of("test@testemail.com"),
                Arguments.of("test@testemail.pl"),
                Arguments.of("test@testemail.eu"));
    }
}
