package com.healthconnect.healthconnect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/projetoRique")
    public String hello() {
        return "Hello Word!";
    }
}
