package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springmvc")
public class HelloSpringController {
    
    @GetMapping("/hello")
    public String helloSpringMVC() {
        return "Hello from Spring MVC!";
    }

    @GetMapping("/hello/again")
    public String helloSpringMVCAgain() {
        return "Hello from Spring MVC again!";
    }
}
