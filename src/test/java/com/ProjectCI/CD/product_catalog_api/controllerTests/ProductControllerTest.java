package com.ProjectCI.CD.product_catalog_api.controllerTests;

import com.ProjectCI.CD.product_catalog_api.controller.ProductController;
import com.ProjectCI.CD.product_catalog_api.model.Product;
import com.ProjectCI.CD.product_catalog_api.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Log
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

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
        when(productService.save(any(Product.class))).thenReturn(true);

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
        when(productService.save(any(Product.class))).thenReturn(false);

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
    public void testFetchAllProducts_ReturnsProducts() throws Exception{
        // mock service to return a list of  products
        when(productService.getAllProducts()).thenReturn(List.of(
                new Product(UUID.randomUUID(), "Product 1", "Des 1", 100.0, new Timestamp(System.currentTimeMillis())),
                new Product(UUID.randomUUID(), "Product 2", "Des 2", 200.0, new Timestamp(System.currentTimeMillis()))
        ));

        // perform get request and Assert the response
        mockMvc.perform(get("/product/getAll").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    public void testFetchAllProduct_HandlesException() throws Exception{
        // mock service to throw Exception
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Database Error"));

        // perform get request and assert the response
        mockMvc.perform(get("/product/getAll").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed());
    }

    @Test
    public void testUpdateProduct_Success() throws Exception{
       // Mock Data
        UUID p_id= UUID.randomUUID();
        Product updatedProduct= new Product(p_id,"Updated Product", "Updated Description",150.0, new Timestamp(System.currentTimeMillis()));

       // Mock service Behavior
       when(productService.updateProduct(eq(p_id) , any(Product.class))).thenReturn(updatedProduct);

       // create request body
        Product requestProduct = new Product(null, "Updated Product ", "Updated description",150.0, new Timestamp(System.currentTimeMillis()));
        String requestBody = objectMapper.writeValueAsString(requestProduct);

       // Perform PUT request and assert the response
        mockMvc.perform(put("/product/Update/{P_ID}", p_id).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(p_id.toString()))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.price").value(150.0));

    }

    @Test
    public void testUpdateProduct_Failure() throws Exception {
        // Mock data
        UUID productId = UUID.randomUUID();

        // Mock service behavior to return null (indicating failure)
        when(productService.updateProduct(eq(productId), any(Product.class))).thenReturn(null);

        // Create request body
        Product requestProduct = new Product(null, "Updated Product", "Updated Description", 150.0, null);
        String requestBody = objectMapper.writeValueAsString(requestProduct);

        // Perform PUT request and assert the response
        mockMvc.perform(put("/product/Update/{P_ID}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isExpectationFailed());
    }
}
