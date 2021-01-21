package com.jsonplaceholder.user.put;

import com.jsonplaceholder.base.BaseTest;
import com.jsonplaceholder.model.Address;
import com.jsonplaceholder.model.Company;
import com.jsonplaceholder.model.Geo;
import com.jsonplaceholder.model.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class PutTest extends BaseTest {

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
        user.setName("Bartosz Update");
        user.setUsername("Bartek");
        user.setEmail("bartek@akademiaqa.pl");
        user.setPhone("123123123");
        user.setWebsite("www.akademiaQa.pl");
        user.setAddress(address);
        address.setGeo(geo);
    }

    @Test()
    public void updateUser() {

        Response response = given()
                .spec(reqSpec)
                .body(user)
                .when()
                .put(BASE_URL + "/" + USERS + "/" + 1)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo(user.getName());
    }
}
