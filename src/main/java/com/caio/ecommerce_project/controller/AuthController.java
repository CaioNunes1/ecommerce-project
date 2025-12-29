// src/main/java/com/caio/ecommerce_project/controller/AuthController.java
package com.caio.ecommerce_project.controller;

import com.caio.ecommerce_project.business.UserService;
import com.caio.ecommerce_project.dto.LoginRequest;
import com.caio.ecommerce_project.infraestructure.entitys.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request){
        User user = userService.authenticate(request.getEmail(), request.getPassword());
        // Em produção: aqui você retornaria um JWT em vez do user (ou junto dele)
        return ResponseEntity.ok(user);
    }
}
