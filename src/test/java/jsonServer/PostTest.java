package jsonServer;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class PostTest {

    String baseURL = "http://localhost:3000/";

    @Test
    public void getPostShouldSucceed(){

        given().baseUri(baseURL)
                .log().uri()
                .when().get("posts")
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test
    public void getPostDetailShouldSucceed(){

        given().baseUri(baseURL)
                .log().uri()
                .when().get("posts/1")
                .then()
                .log().body().statusCode(200)
                .body("id",equalTo("1"))
                .body("title",equalTo("a title"))
                .body("views",equalTo(100));


    }

    @Test
    public void getPostDetail_2_ShouldSucceed(){

        given()
                .baseUri(baseURL)
                .log().uri()
                .when().get("posts/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo("2"))
                .body("title",equalTo("another title"))
                .body("views",equalTo(200));

    }

    @Test
    public void postPostShouldSucceed(){

        String json = "    {\n" +
                "        \"roll\": \"3\",\n" +
                "        \"name\": \"nayeem\",\n" +
                "        \"address\": \"rajshahi\"\n" +
                "    }";

        given()
                .baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("posts")
                .then()
                .log().body()
                .statusCode(201)
                .log().body();

    }

    @Test
    public void postPost_2_ShouldSucceed(){

        Map<String, String> json = new HashMap<>();

        json.put("title", LoremIpsum.getInstance().getTitle(3));
        json.put("name", LoremIpsum.getInstance().getName());
        json.put("city", LoremIpsum.getInstance().getCity());

        given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .post("posts")
                .then()
                .statusCode(201)
                .log().body();

    }

    @Test
    public void updatePostShouldSucceed(){

        Map<String, String> json = new HashMap<>();

        json.put("name",LoremIpsum.getInstance().getName());
        json.put("city",LoremIpsum.getInstance().getCity());
        json.put("email",LoremIpsum.getInstance().getEmail());

        given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .put("posts/2")
                .then()
                .log().body()
                .statusCode(200);



    }

}
