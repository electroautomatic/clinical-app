package com.spartproject.clinicalpatients.clinicalapi.clinicalapi.exeption;

public class ResourceNotFoundException extends RuntimeException {

    // Constructor with error message
    public ResourceNotFoundException(String message) {
      super(message);
    }
  
  }
