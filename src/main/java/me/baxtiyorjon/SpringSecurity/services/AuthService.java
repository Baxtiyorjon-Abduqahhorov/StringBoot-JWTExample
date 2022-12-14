package me.baxtiyorjon.SpringSecurity.services;

import me.baxtiyorjon.SpringSecurity.models.RoleEntity;
import me.baxtiyorjon.SpringSecurity.models.UserEntity;
import me.baxtiyorjon.SpringSecurity.repositories.RoleRepository;
import me.baxtiyorjon.SpringSecurity.repositories.UserRepository;
import me.baxtiyorjon.SpringSecurity.security.TokenGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    public ResponseEntity<String> register(String username, String password) throws RoleNotFoundException {
        if (userRepository.existsByUsername(username)) {
            return new ResponseEntity<>("Username taken", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        RoleEntity role = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return ResponseEntity.ok(username + " registered!");
    }

    public ResponseEntity<Map<String, String>> login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        Map<String, String> res = new HashMap<>();
        res.put("token", token);
        res.put("type", "Bearer");
        return ResponseEntity.ok(res);
    }
}
