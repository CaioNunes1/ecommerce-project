package com.caio.ecommerce_project.controller;

import com.caio.ecommerce_project.business.UserService;
import com.caio.ecommerce_project.infraestructure.entitys.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user){
        return ResponseEntity.status(200).body(service.create(user));
    }
    @GetMapping("/findByEmail")
    public ResponseEntity<User> findByEmail(@RequestParam String email){
        return ResponseEntity.status(200).body(service.findByEmail(email));
    }

    @GetMapping("/getById")
    public ResponseEntity<User> findById(@RequestParam Long id){
        return ResponseEntity.status(200).body(service.findById(id));
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public ResponseEntity<Void> deleteByEmail(@PathVariable String email){
        service.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }




}
