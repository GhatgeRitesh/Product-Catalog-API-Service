package com.ProjectCI.CD.product_catalog_api.controller;

import com.ProjectCI.CD.product_catalog_api.model.Product;
import com.ProjectCI.CD.product_catalog_api.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Log
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Add new product
    @PostMapping("/add")
    public ResponseEntity<?> CreateProduct(@Validated @RequestBody  Product product){

        if(productService.save(product)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    // Retrive all products
    @GetMapping("/getAll")
    public ResponseEntity<List> FetchAllProducts(){
           try {
               List<Product> result = productService.getAllProducts();
               return new ResponseEntity<>(result, HttpStatus.OK);
           }catch(Exception e){
              return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    // Retrive single product with product_ID
    @GetMapping("/getProduct/{P_ID}")
    public ResponseEntity<?> getProduct(@PathVariable("P_ID") UUID uuid){
          log.info("Received request for product ID: " + uuid);
          Product p= productService.getProduct(uuid);
          return new ResponseEntity<>(p,HttpStatus.OK);
    }

    // update product with product_id
    @PutMapping("/Update/{P_ID}")
    public ResponseEntity<?> updateProduct(@PathVariable("P_ID")UUID uuid , @RequestBody Product product){
        Product result  =productService.updateProduct(uuid,product);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


}
