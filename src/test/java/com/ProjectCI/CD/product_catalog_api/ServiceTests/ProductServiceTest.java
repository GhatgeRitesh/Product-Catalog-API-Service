package com.ProjectCI.CD.product_catalog_api.ServiceTests;

import com.ProjectCI.CD.product_catalog_api.exception.ResourceNotFoundException;
import com.ProjectCI.CD.product_catalog_api.model.Product;
import com.ProjectCI.CD.product_catalog_api.repository.ProductRepo;
import com.ProjectCI.CD.product_catalog_api.service.ProductService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Log
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepo productRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProductService_Success() {
        //Mock Product Objects
        Product product = new Product();
        product.setName("Test Product Name");
        product.setDescription("Test Product Description");
        product.setPrice(200.00);

        // Mock Repository Behaviour
        when(productRepo.save(any(Product.class))).thenReturn(product);

        // call service Method
        boolean result = productService.save(product);

        // Assert the result
        assertTrue(result, "Product Should be saved successfully");
        verify(productRepo, times(1)).save(product);  //verfiy the save method is called once
    }

    @Test
    public void testProductService_Failure() {
        // mock product objects
        Product product = new Product();
        product.setName("Test Fail product Name");
        product.setDescription("Test Fail Description");
        product.setPrice(0.00);


        // mock repository behaviour to stimulate failure
        when(productRepo.save(any(Product.class))).thenThrow(new RuntimeException("Database Error"));

        // call the service method
        boolean result = productService.save(product);

        //assert the results
        assertFalse(result, "Product Save should fail due to repository exception");
        verify(productRepo, times(1)).save(product); // verify the method is called once


    }

    @Test
    public void testGetAllProducts_Success() throws Exception {
        // Mock data
        List<Product> mockProducts = Arrays.asList(
                new Product(UUID.randomUUID(), "Product 1", "Description 1", 100.0, new Timestamp(System.currentTimeMillis())),
                new Product(UUID.randomUUID(), "Product 2", "Description 2", 200.0, new Timestamp(System.currentTimeMillis()))
        );

        // Mock repository behavior
        when(productRepo.findAll()).thenReturn(mockProducts);

        // Call the method under test
        List<Product> result = productService.getAllProducts();

        // Assertions
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Product 1");
        assertThat(result.get(1).getName()).isEqualTo("Product 2");

        // Verify the data matches the mocked data
        assertThat(result).isEqualTo(mockProducts);
    }

    @Test
    public void testGetAllProducts_EmptyList() throws Exception {
        // Mock repository to return an empty list
        when(productRepo.findAll()).thenReturn(List.of());

        // Call the method under test
        List<Product> result = productService.getAllProducts();

        // Assertions
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    public void testUpdateProduct_Success() {
        // Mock data
        UUID productId = UUID.randomUUID();
        Product oldProduct = new Product(productId, "Old Name", "Old Description", 100.0, new Timestamp(System.currentTimeMillis()));
        Product updatedProduct = new Product(null, "Updated Name", "Updated Description", 150.0, null);

        // Mock repository behavior
        when(productRepo.getReferenceById(productId)).thenReturn(oldProduct);
        doNothing().when(productRepo).update(
                eq(productId),
                eq(updatedProduct.getName()),
                eq(updatedProduct.getDescription()),
                eq(updatedProduct.getPrice()),
                any(Timestamp.class)
        );

        // Call the service method
        Product result = productService.updateProduct(productId, updatedProduct);

        // Assertions
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        assertThat(result.getPrice()).isEqualTo(150.0);

        // Verify interactions with mocks
        verify(productRepo, times(1)).getReferenceById(productId);
        verify(productRepo, times(1)).update(
                eq(productId),
                eq(updatedProduct.getName()),
                eq(updatedProduct.getDescription()),
                eq(updatedProduct.getPrice()),
                any(Timestamp.class)
        );
    }

    @Test
    public void testUpdateProduct_Failure() {
        // Mock data
        UUID productId = UUID.randomUUID();
        Product updatedProduct = new Product(null, "Updated Name", "Updated Description", 150.0, null);

        // Mock repository behavior to throw exception
        when(productRepo.getReferenceById(productId)).thenThrow(new RuntimeException("Database Error"));

        // Call the service method
        Product result = productService.updateProduct(productId, updatedProduct);

        // Assertions
        assertThat(result).isNull();

        // Verify interactions with mocks
        verify(productRepo, times(1)).getReferenceById(productId);
        verify(productRepo, never()).update(any(), any(), any(), any(), any());
    }

    // get product with id test success
    @Test
    public void testGetProductSuccess() {
        //Mock Object
        UUID productID = UUID.randomUUID();
        Product product = new Product(productID, "The Product Name", "The Product Description", 100.0, new Timestamp(System.currentTimeMillis()));

        // mock behaviour
        Mockito.when(productRepo.findById(productID)).thenReturn(Optional.of(product));

        Product result = productService.getProduct(productID);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(product);
    }

    @Test
    public void testGetProduct_NotFound() {
        // mock uuid
        UUID uuid = UUID.randomUUID();

        //
        Mockito.when(productRepo.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(uuid));

    }
}