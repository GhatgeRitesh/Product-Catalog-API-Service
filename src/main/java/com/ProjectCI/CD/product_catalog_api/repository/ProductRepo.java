package com.ProjectCI.CD.product_catalog_api.repository;

import com.ProjectCI.CD.product_catalog_api.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product,UUID> {

    @Modifying
    @Transactional
    @Query("Update Product p SET p.name = :name , p.description= :description , p.price= :price , p.timestamp= :timestamp Where p.uuid= :uuid")
    void update(@Param("uuid") UUID uuid,
                @Param("name") String name,
                @Param("description") String description,
                @Param("price") Double price,
                @Param("timestamp")Timestamp timestamp
                );
}
