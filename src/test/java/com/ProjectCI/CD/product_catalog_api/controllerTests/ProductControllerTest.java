package com.ProjectCI.CD.product_catalog_api.controllerTests;

import com.ProjectCI.CD.product_catalog_api.controller.ProductController;
import com.ProjectCI.CD.product_catalog_api.model.Product;
import com.ProjectCI.CD.product_catalog_api.service.ProductService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Log
@SpringBootTest
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testCreateProduct_Success() throws Exception {
      //  mockMvc = MockMvcBuilders.standaloneSetup(productController).build(); this is the creation of the object to interact MVC as mock
        // can be done inside or outside method (for multiple use)


        // Mock service behavior
        when(productService.save(Mockito.any(Product.class))).thenReturn(true);

        // Perform POST request and assert the response
        mockMvc.perform(post("/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ " +
                                "\"uuid\": \"c56a4180-65aa-42ec-a945-5fd21dec0538\"," +
                                "\"name\": \"Test Product\"," +
                                "\"description\": \"This is a test product\"," +
                                "\"price\": 100.0," +
                                "\"timestamp\": \"2025-01-19T12:00:00.000Z\"" +
                                "}"))
                .andExpect(status().isOk());

    }

    @Test
    public void testCreateProduct_Failure() throws Exception {
        // create mvcMock Object if not create above


        // mock service behavior
        when(productService.save(Mockito.any(Product.class))).thenReturn(false);

        // perform POST request and assert the response
        mockMvc.perform(post("/product/add").contentType(MediaType.APPLICATION_JSON).content(
                "{ " +
                        "\"uuid\": \"c56a4180-65aa-42ec-a945-5fd21dec0538\"," +
                        "\"name\": \"Invalid Product\"," +
                        "\"description\": \"Invalid product description\"," +
                        "\"price\": 200.0," +
                        "\"timestamp\": \"2025-01-19T12:00:00.000Z\"" +
                        "}"
        )).andExpect(status().isExpectationFailed());
    }

    @Test
    public void testFetchAllProducts_ReturnsNoContent() throws Exception {
        // mock service to return an empty list
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());

        // Perfrom Get Request And Assert the response
        mockMvc.perform(get("/product/getAll").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect( content().string("[]"));
    }

    @Test
    public void testFetchAllProducts_ReturnsProducts() throws Exception{
        // mock service to return a list of  products
        when(productService.getAllProducts()).thenReturn(new ArrayList<>(Arrays.asList(
                new Product(UUID.randomUUID(),"Product 1","Des 1", 100.0),
                new Product(UUID.randomUUID(),"product 2", "des 2", 200.0)
        )));

        // perform get request and Assert the response
        mockMvc.perform(get("/product/getAll").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("product 2"));
    }

    @Test
    public void testFetchAllProduct_HandlesException() throws Exception{
        // mock service to throw Exception
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Database Error"));

        // perform get request and assert the response
        mockMvc.perform(get("/product/getAll").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed());
    }
}
