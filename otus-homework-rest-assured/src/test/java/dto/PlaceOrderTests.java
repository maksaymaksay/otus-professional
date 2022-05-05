package dto;

import dto.store.Order;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import service.StoreOrderApi;

import java.time.LocalDate;

public class PlaceOrderTests {
    StoreOrderApi storeOrderApi = new StoreOrderApi();

    @Test
    public void checkResponseFields() {
        Order order = Order.builder()
                .id(100L)
                .petId(12L)
                .quantity(1L)
                .shipDate(LocalDate.now().toString())
                .status("placed")
                .complete(true)
                .build();

        Response response = storeOrderApi.placeOrder(order);
        response
                .then()
                .log().all()
                .statusCode(200);

        Order orderResponse = response.then().extract().as(Order.class);
        //Проверяем id
        long actualId = orderResponse.getId();
        Assert.assertEquals(100L, actualId);
        //Проверяем petId
        long actualPetId = orderResponse.getPetId();
        Assert.assertEquals(12L, actualPetId);
        //Проверяем quantity
        long actualQuantity = orderResponse.getQuantity();
        Assert.assertEquals(1L, actualQuantity);
        //Проверяем shipDate
        String actualShipDate = orderResponse.getShipDate();
        Assert.assertTrue(actualShipDate.contains(LocalDate.now().toString()));
        //Проверяем status
        String actualStatus = orderResponse.getStatus();
        Assert.assertEquals("placed", actualStatus);
        //Проверяем complete
        Boolean actualComplete = orderResponse.getComplete();
        Assert.assertEquals(true, actualComplete);
    }

    @Test
    public void checkStatus400() {
        Response response = storeOrderApi.placeOrder("null");
        response
                .then()
                .log().all()
                .statusCode(400);
    }
}
