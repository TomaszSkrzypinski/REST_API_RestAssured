import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class OrganizationTest extends BaseTest{

    private static Stream <Arguments> createOrganizationData() {
        return Stream.of(
                Arguments.of("Akademia QA jest OK", "https://.www.wp.pl", "My organization name"),
                Arguments.of("Akademia QA jest OK", "http://.www.wp.pl", "My organization name"));
    }

    private static Stream <Arguments> createNameData() {
        return Stream.of(
                Arguments.of("my_organization_name123", "my_organization_name123"),
                Arguments.of("My Organization Name", "myorganizationname"),
                Arguments.of("organiza!@#tion15190", "organization15190"));
    }

    private static Stream <Arguments> createWebsiteData() {
        return Stream.of(
                Arguments.of("http://www.wpw.pl"),
                Arguments.of("www.wp.pl"),
                Arguments.of("www://wp.pl"));
    }

    @DisplayName("Create organization with valid data")
    @ParameterizedTest(name = "desc: {0}, website: {1}, name: {2},}")
    @MethodSource("createOrganizationData")
    public void createNewOrganization(String description, String website, String name) {

        Organization organization = new Organization();

        organization.setDesc(description);
        organization.setWebsite(website);
        organization.setName(name);

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", organization.getName())
                .queryParam("desc", organization.getDesc())
                .queryParam("website", organization.getWebsite())
                .when()
                .post(BASE_URL + ORGANIZATIONS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("id");

        Assertions.assertThat(json.getString("desc")).isEqualTo(organization.getDesc());
    }

    @DisplayName("Testing organization name")
    @ParameterizedTest(name = "name: {0}, answer: {1}")
    @MethodSource("createNameData")
    public void testsOrganizationName(String name, String answer) {

        Organization organization = new Organization();

        organization.setName(name);

        Response response = given()
                .spec(reqSpec)
                .queryParam("name", organization.getName())
                .when()
                .post(BASE_URL + ORGANIZATIONS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("id");

        Assertions.assertThat(json.getString("name")).isEqualTo(answer);
    }

    @DisplayName("Testing organization WWW")
    @ParameterizedTest(name = "website: {0}")
    @MethodSource("createWebsiteData")
    public void testsOrganizationWWW(String website) {

        Organization organization = new Organization();

        organization.setWebsite(website);

        Response response = given()
                .spec(reqSpec)
                .queryParam("website", organization.getWebsite())
                .when()
                .post(BASE_URL + ORGANIZATIONS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("id");

        Assertions.assertThat(json.getString("website")).startsWith("http://");
    }
}