import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class PostTest {


    //Stary sposób dla przypomnienia
    @Test
    public void createPost(){

        JSONObject post = new JSONObject();
        post.put("userId", 1);
        post.put("title", "post title");
        post.put("body", "post body");

        Response response = given()
                .when()
                .body(post.toString())
                .post("http://jsonplaceholder.typicode.com/posts/")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("title")).isEqualTo("post title");
    }

    //Z użyciem serializacji
    @Test
    public void createPostWithSerialization(){

        //POJO
        Post post = new Post();
        post.setBody("post body");
        post.setTitle("post title");
        post.setUserId(1);

        //POJO -> JSON
        Response response = given()
                .when()
                .body(post)
                .contentType(ContentType.JSON)
                .post("http://jsonplaceholder.typicode.com/posts/")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("title")).isEqualTo(post.getTitle());
    }

    @Test
    public void readPost(){

        Post post = given()
                .when()
                .get("http://jsonplaceholder.typicode.com/posts/1")
                .as(Post.class);

        assertThat(post.getTitle()).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    }
}
