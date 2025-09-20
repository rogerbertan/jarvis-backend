package com.bertan.jarvis_backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Value("${project.version:1.0.0}")
    private String version;

    @GetMapping
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "service", "jarvis-backend",
                "version", version);
    }

}
