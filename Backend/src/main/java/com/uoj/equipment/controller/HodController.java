package com.uoj.equipment.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hod")
public class HodController {

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of("status", "hod-ok");
    }
}
