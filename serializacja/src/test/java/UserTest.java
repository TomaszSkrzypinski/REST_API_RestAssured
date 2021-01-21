import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class UserTest {


    @Test
    public void createNewUser(){

        User user = new User();
        user.setEmail("email@wp");
        user.setUsername("Tomek");
        user.setName("Tomek Testowy");
        user.setPhone("662182676");
        user.setWebsite("www.wp.pl");

        Geo geo = new Geo();
        geo.setLat("-37.3159");
        geo.setLng("81.1496");

        Address address = new Address();
        address.setCity("Swaj");
        address.setGeo(geo);
        address.setSuite("Osiedle");
        address.setStreet("Czwartaków");
        address.setZipcode("4587-9856");

        user.setAddress(address);

        Company company = new Company();
        company.setBs("harness real-time e-markets");
        company.setCatchPhrase("Multi-layered client-server neural-net");
        company.setName("Moja firma");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("http://jsonplaceholder.typicode.com/users/")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("address.city")).isEqualTo(user.getAddress().getCity());
    }
}
