package com.ProjectCI.CD.product_catalog_api.controller;

import com.ProjectCI.CD.product_catalog_api.model.Product;
import com.ProjectCI.CD.product_catalog_api.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Log
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> CreateProduct(@Validated @RequestBody  Product product){
        log.info("Adding Product To Catalog" + product.toString());
        if(productService.save(product)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> FetchAllProducts(){
        log.info("fetching all products");
        try {
            ArrayList<Product> result = productService.getAllProducts();
            if(result.isEmpty()){ log.info("Empty Result");
             return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(Exception e){
            log.info("Exception while calling product service "+e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


}
