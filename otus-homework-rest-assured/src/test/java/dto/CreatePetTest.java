package dto;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import service.PetApi;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreatePetTest {
    PetApi petApi = new PetApi();

    @Test
    public void checkCreatePet(){
        Pet pet = Pet.builder()
                .id(1L)
                .category(Category.builder().id(1L).name("Cat").build())
                .name("superCat")
                .photoUrls(Collections.singletonList("catPic"))
                .tags(Collections.singletonList(Tag.builder().id(1L).name("cat").build()))
                .status("available")
                .build();

        petApi.createPet(pet)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("superCat"))
                .body("category.name", equalTo("Cat"));

        Response response = petApi.createPet(pet);

        String actualStatus = response.jsonPath().get("status");
        Assert.assertEquals("available", actualStatus);
    }
}