package me.baxtiyorjon.SpringSecurity.controllers;

import me.baxtiyorjon.SpringSecurity.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam("username") String username, @RequestParam("password") String password) throws RoleNotFoundException {
        return authService.register(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return authService.login(username, password);
    }
}
