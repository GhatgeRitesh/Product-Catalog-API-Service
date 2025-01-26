package com.ProjectCI.CD.product_catalog_api.ServiceTests;

import com.ProjectCI.CD.product_catalog_api.model.Product;
import com.ProjectCI.CD.product_catalog_api.repository.ProductRepo;
import com.ProjectCI.CD.product_catalog_api.service.ProductService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Timestamp;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@Log
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepo productRepo;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProductService_Success(){
        //Mock Product Objects
        Product product = new Product();
        product.setName("Test Product Name");
        product.setDescription("Test Product Description");
        product.setPrice(200.00);

        // Mock Repository Behaviour
        when(productRepo.save(any(Product.class))).thenReturn(product);

        // call service Method
        boolean result= productService.save(product);

        // Assert the result
        assertTrue(result, "Product Should be saved successfully");
        verify(productRepo,times(1)).save(product);  //verfiy the save method is called once
    }

    @Test
    public void testProductService_Failure(){
        // mock product objects
        Product product= new Product();
        product.setName("Test Fail product Name");
        product.setDescription("Test Fail Description");
        product.setPrice(0.00);


        // mock repository behaviour to stimulate failure
        when(productRepo.save(any(Product.class))).thenThrow(new RuntimeException("Database Error"));

        // call the service method
        boolean result= productService.save(product);

        //assert the results
        assertFalse(result,"Product Save should fail due to repository exception");
        verify(productRepo,times(1)).save(product); // verify the method is called once


    }

}
