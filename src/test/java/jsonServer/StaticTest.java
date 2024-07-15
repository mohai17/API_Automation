package jsonServer;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class StaticTest {

    String baseURL = "http://localhost:3000/";

    @Test(priority = 0)
    public void getPostShouldSucceed() {

        given().baseUri(baseURL)
                .log().uri()
                .when().get("posts")
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(priority = 1)
    public void getPostDetailShouldSucceed() {

        given().baseUri(baseURL)
                .log().uri()
                .when().get("posts/1")
                .then()
                .log().body().statusCode(200)
                .body("id", equalTo("1"))
                .body("title", equalTo("a title"))
                .body("views", equalTo(100));


    }

    @Test(priority = 2)
    public void getPostDetail_2_ShouldSucceed() {

        given()
                .baseUri(baseURL)
                .log().uri()
                .when().get("posts/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo("2"))
                .body("title", equalTo("another title"))
                .body("views", equalTo(200));

    }

    @Test(priority = 3)
    public void postPostShouldSucceed() {

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

    @Test(priority = 4)
    public void putPostShouldSucceed() {

        String json = "  {\n" +
                "    \"id\": \"1\",\n" +
                "    \"email\": \"example@mail.com\",\n" +
                "    \"views\": 100\n" +
                "  }";

        given()
                .baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .body(json)
                .log().body()
                .when()
                .put("posts/1")
                .then()
                .log().body()
                .statusCode(200);


    }

    @Test(priority = 5)
    public void patchPostShouldSucceed() {

        String json = "{\"views\":\"3050\"}";

        given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .body(json)
                .log().body()
                .when()
                .patch("posts/2")
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(priority = 6)
    public void deletePostShouldSucceed() {
        given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .when()
                .delete("posts/1")
                .then()
                .statusCode(200);
    }

}