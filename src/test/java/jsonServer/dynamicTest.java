package jsonServer;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class dynamicTest {

    String baseURL = "http://localhost:3000/";


    @Test(priority = 0)
    public void getPostShouldSucceed()
    {

        String id = given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .when()
                .get("posts/")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");

        given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .get("posts/"+id)
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(priority = 1)
    public void postPostShouldSucceed()
    {

        Map<String,String> json = new HashMap<>();

        json.put("name",LoremIpsum.getInstance().getName());
        json.put("city",LoremIpsum.getInstance().getCity());
        json.put("email",LoremIpsum.getInstance().getEmail());

        given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .body(json)
                .log().body()
                .when()
                .post("posts/")
                .then()
                .statusCode(201);

    }

    @Test(priority = 2)
    public void putPostShouldSucceed(){

        String id = given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .when()
                .get("posts")
                .then()
                .log().body()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");

        Map<String, String> json = new HashMap<>();

        json.put("name",LoremIpsum.getInstance().getName());
        json.put("city",LoremIpsum.getInstance().getCity());
        json.put("email",LoremIpsum.getInstance().getEmail());


        given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .body(json)
                .log().body()
                .when()
                .put("posts/"+id)
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(priority = 3)
    public void patchPostShouldSucceed()
    {
        String id = given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .when()
                .get("posts")
                .then()
                .log().body()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");

        Map<String, String> json = new HashMap<>();

        json.put("email",LoremIpsum.getInstance().getEmail());


        given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .body(json)
                .log().body()
                .when()
                .patch("posts/"+id)
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(priority = 4)
    public void deletePostShouldSucceed()
    {
        String id = given().baseUri(baseURL)
                .port(3000)
                .contentType(ContentType.JSON)
                .when()
                .get("posts")
                .then()
                .log().body()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");


        given().baseUri(baseURL)
                .port(3000)
                .when()
                .delete("posts/"+id)
                .then()
                .statusCode(200);
    }


}
