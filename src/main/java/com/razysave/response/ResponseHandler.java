package com.razysave.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("status",status.toString());
        map.put("response",responseObj);
        return new ResponseEntity<Object>(map,status);
    }
}
