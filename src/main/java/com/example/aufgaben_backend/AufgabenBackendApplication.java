package com.example.aufgaben_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AufgabenBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AufgabenBackendApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "✅ Server is running (main class)!";
    }
}
