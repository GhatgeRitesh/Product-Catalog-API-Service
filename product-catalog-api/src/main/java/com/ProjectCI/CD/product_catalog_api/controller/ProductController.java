package com.ProjectCI.CD.product_catalog_api.controller;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@RequestMapping("/product")
public class ProductController {


    public String home(){
        System.out.println("This is test");return "OK";
    }
}
