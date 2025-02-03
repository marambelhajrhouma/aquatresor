package com.example.AquaTresor.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")  // Autorise uniquement les requêtes venant de localhost:4200
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Hana";
    }
}
