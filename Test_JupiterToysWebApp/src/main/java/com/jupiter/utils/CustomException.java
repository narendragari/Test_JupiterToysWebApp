package com.jupiter.utils;

public class CustomException extends RuntimeException
{
    private String strCustomMessage;
    
    public CustomException() {
        this("Error performing the required operation");
    }
    
    public CustomException(final String strMessage) {
        this.strCustomMessage = strMessage;
    }
    
    @Override
    public String toString() {
        return this.strCustomMessage;
    }
}