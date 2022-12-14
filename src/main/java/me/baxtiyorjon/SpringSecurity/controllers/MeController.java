package me.baxtiyorjon.SpringSecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {

    @GetMapping("/name")
    public String getName() {
        return "Baxtiyorjon";
    }
}
