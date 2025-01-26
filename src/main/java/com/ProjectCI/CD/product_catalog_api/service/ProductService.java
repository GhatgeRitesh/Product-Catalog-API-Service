package com.ProjectCI.CD.product_catalog_api.service;

import com.ProjectCI.CD.product_catalog_api.model.Product;
import com.ProjectCI.CD.product_catalog_api.repository.ProductRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Log
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public boolean save(Product product){
        try {
        if(product.getTimestamp() == null){
            product.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }
        productRepo.save(product);
        return true;
        }catch(Exception e){
            log.info("Exception While Adding Product : service class " + e);
            return false;
        }
    }

    public ArrayList<Product> getAllProducts(){
        try{
            ArrayList<Product> result=(ArrayList<Product>)productRepo.findAll();
            return result;
        }catch(Exception e){
            log.info("Exception while fetching all product "+e);
            return new ArrayList<>(null);
        }
    }
}
