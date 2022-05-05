package dto;

import dto.pet.Category;
import dto.pet.Pet;
import dto.pet.Tag;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import service.PetApi;

import java.util.Collections;
import java.util.List;

public class CreatePetTests {
    PetApi petApi = new PetApi();

    @Test
    public void checkStatusCreatePet() {
        Pet pet = Pet.builder()
                .id(1L)
                .category(Category.builder().id(1L).name("Cat").build())
                .name("superCat")
                .photoUrls(Collections.singletonList("catPic"))
                .tags(Collections.singletonList(Tag.builder().id(1L).name("cat").build()))
                .status("available")
                .build();

        Response response = petApi.createPet(pet);
        response
                .then()
                .log().all()
                .statusCode(200);

        String actualStatus = response.jsonPath().get("status");
        Assert.assertEquals("available", actualStatus);
    }

    @Test
    public void checkNamesCreatePet() {
        Pet pet = Pet.builder()
                .id(1L)
                .category(Category.builder().id(1L).name("Cat").build())
                .name("superCat")
                .photoUrls(Collections.singletonList("catPic"))
                .tags(Collections.singletonList(Tag.builder().id(1L).name("cat").build()))
                .status("available")
                .build();

        Response response = petApi.createPet(pet);
        response
                .then()
                .log().all()
                .statusCode(200);

        Pet petResponse = response.then().extract().as(Pet.class);

        //Проверяем Имя в поле Name
        String actualName = petResponse.getName();
        Assert.assertEquals("superCat", actualName);

        //Проверяем Имя в объекте Категория
        String actualCategoryName = petResponse.getCategory().getName();
        Assert.assertEquals("Cat", actualCategoryName);

        //Проверяем Имя в массиве Tags
        List<Tag> tags = petResponse.getTags();
        String actualTagName = tags.get(0).getName();
        Assert.assertEquals("cat", actualTagName);
    }

    @Test
    public void checkStatus405() {
        Response response = petApi.createPet("null");
        response
                .then()
                .log().all()
                .statusCode(405);
    }
}