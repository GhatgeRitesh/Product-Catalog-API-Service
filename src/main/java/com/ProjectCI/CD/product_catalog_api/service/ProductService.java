package com.ProjectCI.CD.product_catalog_api.service;

import com.ProjectCI.CD.product_catalog_api.model.Product;
import com.ProjectCI.CD.product_catalog_api.repository.ProductRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public boolean save(Product product){
        log.info("Adding Product To Catalog" + product.toString());
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

    public List<Product> getAllProducts() throws Exception{
            log.info("Fetching Product List");
            List<Product> result=productRepo.findAll();
            log.info("Fetched Successfully");
            return result;
    }

    public Product updateProduct( UUID uuid , Product product){
        try{
            log.info("retrieving Old Details");
            Product old_product = productRepo.getReferenceById(uuid);
            log.info("Old Details :" + old_product.toString());

            log.info("Updating Product Details to :" + product.toString());
            product.setTimestamp(new Timestamp(System.currentTimeMillis()));
            productRepo.update(uuid,
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getTimestamp());

            log.info("Product Details Updated Successfully");
            return product;
        }catch (Exception e){
            log.info("Exception occurred while updating product details  of product_id "+ uuid + " "+e);
            return null;
        }
    }
}
