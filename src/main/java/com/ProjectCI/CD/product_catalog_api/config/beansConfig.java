package com.ProjectCI.CD.product_catalog_api.config;

import com.ProjectCI.CD.product_catalog_api.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class beansConfig {

    @Bean
    public Product product(){
        return new Product();
    }
}
