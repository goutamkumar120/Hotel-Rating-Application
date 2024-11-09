package com.gk.user.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource not found on srever !!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
