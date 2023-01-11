package com.example.alishevTask.ExceptionHandler;

public class SensorExistException extends RuntimeException{

    public SensorExistException(String message) {
        super(message);
    }
}
