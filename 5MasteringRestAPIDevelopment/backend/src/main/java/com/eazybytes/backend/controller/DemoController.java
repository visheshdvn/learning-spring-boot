package com.eazybytes.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

//    @GetMapping("/home")
    @RequestMapping(
            value = "/home",
            method = {RequestMethod.GET,RequestMethod.POST}
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String sayHello() {
        return "Hello World!";
    }
    
}
