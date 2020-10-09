package com.java.syxxn.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test{

    @GetMapping("/test")
    public String wa(){

        return "Hello World";
    }
}