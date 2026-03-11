package com.healthconnect.healthconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "index"; // certifique-se de ter src/main/resources/templates/index.html
    }
}