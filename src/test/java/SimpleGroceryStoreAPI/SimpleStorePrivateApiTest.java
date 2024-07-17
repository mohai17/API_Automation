package SimpleGroceryStoreAPI;


import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SimpleStorePrivateApiTest {

    String baseURL = "https://simple-grocery-store-api.glitch.me";
    String bearerToken = "629aa75e43c5a3103c71524b36f9e81e2bc41a3b8a87be046e7dc66e3ac812cd";


    public String getToken(){

        JSONObject json = new JSONObject();

        json.put("clientName", "nayeem");
        json.put("clientEmail", "nayeem@gmail.com");

        String bearerToken = given().baseUri(baseURL)
                .header("content-type","application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .post("/api-clients")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("accessToken");

        return bearerToken;

    }

    public int getProductId(){

        return given().baseUri(baseURL)
                .contentType(ContentType.JSON)
                .when()
                .get("/products")
                .then()
                .log().body()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");

    }

    public String getCartId(){

        return given().baseUri(baseURL)
                .contentType(ContentType.JSON)
                .when()
                .post("/carts")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("cartId");

    }


    public void addItems()
    {
        JSONObject json = new JSONObject();
        json.put("cartId",getCartId());
        json.put("productId",getProductId());


        given().baseUri(baseURL)
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/carts/"+getCartId()+"/items")
                .then()
                .log().body()
                .statusCode(201);

    }

    @Test
    public String getOrderId(){

        JSONObject json = new JSONObject();
        json.put("cartId",getCartId());
        json.put("customerName","nayeem");

        return given().baseUri(baseURL)
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer " + bearerToken)
                .body(json)
                .when()
                .post("/orders")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("orderId");

    }

    @Test
    public void getSingleOrder(){

        given().baseUri(baseURL)
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer " + bearerToken)
                .when()
                .get("/orders/"+getOrderId())
                .then()
                .statusCode(200);

    }

    @Test
    public void getAllOrders(){

        given().baseUri(baseURL)
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer " + bearerToken)
                .when()
                .get("/orders")
                .then()
                .log().body()
                .statusCode(200);



    }
}
