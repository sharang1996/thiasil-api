package com.thiasil.thiasil_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiasil.thiasil_api.model.Dimensions;
import com.thiasil.thiasil_api.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProductControllerTest extends E2ETest {

    private Product product;

    private ResultActions resultActions;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canGetProductsByCategory() throws Exception {
        givenMultipleProductsOfCategoryAlreadyExists();
        whenGetProductsForCategoryRequestIsSent();
        thenExpectOnlyProductsForCategoryAreReturned();
    }

    @Test
    void canCreateProduct() throws Exception {
        givenAProduct();
        whenProductCreateRequestIsSent();
        thenExpectProductIsCreated();
    }

    @Test
    void canUpdateProduct() {
    }

    @Test
    void canDeleteProduct() {
    }

    @Test
    void canCreateProducts() {
    }

    @Test
    void canGetAllProducts() throws Exception {
        givenMultipleProductsOfCategoryAlreadyExists();
        whenGetAllProductsRequestIsSent();
        thenExpectAllProductsAreReturned();
    }

    private void givenAProduct() {
        Dimensions dimension = new Dimensions(UUID.randomUUID(), 10,20,30,5,10,1);
        product = new Product(UUID.randomUUID(), "testCategory", "test1Catalogue", 10, 25, dimension, "testConfiguration", 250.0);
    }

    private void givenAnotherProduct() {
        Dimensions dimension = new Dimensions(UUID.randomUUID(), 10,20,30,5,10,1);
        product = new Product(UUID.randomUUID(), "testCategory", "test2Catalogue", 10, 25, dimension, "testConfiguration", 300);
    }

    private void givenThirdProduct() {
        Dimensions dimension = new Dimensions(UUID.randomUUID(), 10,20,30,5,10,1);
        product = new Product(UUID.randomUUID(), "test2Category", "test3Catalogue", 10, 25, dimension, "testConfiguration", 300);
    }

    private void givenMultipleProductsOfCategoryAlreadyExists() throws Exception {
        givenAProduct();
        createProduct();
        givenAnotherProduct();
        createProduct();
        givenThirdProduct();
        createProduct();
    }

    private void createProduct() throws Exception {
        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product)));
    }

    private void thenExpectProductIsCreated() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("testCategory"))
                .andExpect(jsonPath("$.catalogueNumber").value("test1Catalogue"))
                .andExpect(jsonPath("$.packageQuantity").value(10))
                .andExpect(jsonPath("$.capacity").value(25))
                .andExpect(jsonPath("$.dimensions.length").value(10))
                .andExpect(jsonPath("$.dimensions.width").value(20))
                .andExpect(jsonPath("$.dimensions.height").value(30))
                .andExpect(jsonPath("$.dimensions.diameter").value(5))
                .andExpect(jsonPath("$.dimensions.maxDiameter").value(10))
                .andExpect(jsonPath("$.dimensions.minDiameter").value(1))
                .andExpect(jsonPath("$.configuration").value("testConfiguration"))
                .andExpect(jsonPath("$.price").value(250));
    }

    private void thenExpectOnlyProductsForCategoryAreReturned() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].category").value("testCategory"))
                .andExpect(jsonPath("$.[0].catalogueNumber").value("test1Catalogue"))
                .andExpect(jsonPath("$.[1].category").value("testCategory"))
                .andExpect(jsonPath("$.[1].catalogueNumber").value("test2Catalogue"));
    }

    private void thenExpectAllProductsAreReturned() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[0].category").value("testCategory"))
                .andExpect(jsonPath("$.[0].catalogueNumber").value("test1Catalogue"))
                .andExpect(jsonPath("$.[1].category").value("testCategory"))
                .andExpect(jsonPath("$.[1].catalogueNumber").value("test2Catalogue"))
                .andExpect(jsonPath("$.[2].category").value("test2Category"))
                .andExpect(jsonPath("$.[2].catalogueNumber").value("test3Catalogue"));
    }

    private void whenProductCreateRequestIsSent() throws Exception {
        resultActions = mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product)));
    }

    private void whenGetProductsForCategoryRequestIsSent() throws Exception {
        resultActions = mockMvc.perform(get("/api/v1/product?category=testCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product)));
    }

    private void whenGetAllProductsRequestIsSent() throws Exception {
        resultActions = mockMvc.perform(get("/api/v1/product/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product)));
    }

}