package service;

import dto.store.Order;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class StoreOrderApi {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PATH = "/store/order";

    private RequestSpecification specification;

    public StoreOrderApi() {
        specification = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
    }

    public Response placeOrder(Order order) {
        return given(specification)
                .log().all()
                .body(order)
                .when()
                .post(PATH);
    }

    public Response placeOrder(String json) {
        return given(specification)
                .log().all()
                .body(json)
                .when()
                .post(PATH);
    }
}
