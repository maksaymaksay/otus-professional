package service;

import dto.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PetApi {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String PATH = "/pet";

    private RequestSpecification specification;

    public PetApi(){
        specification = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
    }

    public Response createPet(Pet pet){
        return given(specification)
                .log().all()
                .body(pet)
                .when()
                .post(PATH);
    }
}
