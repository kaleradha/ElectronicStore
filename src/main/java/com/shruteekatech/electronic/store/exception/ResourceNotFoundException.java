package com.shruteekatech.electronic.store.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        return ;
    }

    public ResourceNotFoundException(String message){
        super (message);
    }
}
