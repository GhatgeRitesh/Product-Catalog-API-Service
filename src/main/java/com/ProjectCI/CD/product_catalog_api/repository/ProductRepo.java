package com.ProjectCI.CD.product_catalog_api.repository;

import com.ProjectCI.CD.product_catalog_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product,UUID> {
}
