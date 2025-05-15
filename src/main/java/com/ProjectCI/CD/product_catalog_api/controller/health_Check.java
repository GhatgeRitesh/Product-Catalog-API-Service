package com.ProjectCI.CD.product_catalog_api.controller;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@RequestMapping("/pcas")
public class health_Check {

    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
