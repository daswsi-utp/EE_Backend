package com.auth_service.controllers;

import com.auth_service.dto.request.ChangePasswordRequest;
import com.auth_service.dto.request.LoginRequest;
import com.auth_service.dto.request.UserRequest;
import com.auth_service.dto.response.RegisterResponse;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.service.daoImpl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    //Logear y devolver token de usuario
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try {
            RegisterResponse response = authService.register(userRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno en el servidor"));
        }
    }


    //Jalar a todo los usuarios
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        try {
            List<UserResponse> users = authService.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //buscar usuario por ID
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        try {
            UserResponse user = authService.getUserById(id);  // método a implementar
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Cambiar contraseña
    @PutMapping("/users/{username}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable String username, @RequestBody ChangePasswordRequest request) {
        try {
            String result = authService.changePassword(username, request);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Desactivar usuario por ID
    @PutMapping("/users/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        try {
            String result = authService.deactivateUser(id);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
