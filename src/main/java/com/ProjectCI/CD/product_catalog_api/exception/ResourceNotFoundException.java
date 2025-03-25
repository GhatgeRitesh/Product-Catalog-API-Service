package com.ProjectCI.CD.product_catalog_api.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String Message){
        super(Message);
    }
}
