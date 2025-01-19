package com.ProjectCI.CD.product_catalog_api.controllerTests;

import com.ProjectCI.CD.product_catalog_api.controller.ProductController;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
public class ProductControllerTest {

    @Test
    void testhomeMethod(){

        //Arrange
        ProductController pc=new ProductController();

        //Act
        String result= pc.home();

        //Assert
        assertEquals("OK",result,"The home method should return 'OK'");

    }
}
