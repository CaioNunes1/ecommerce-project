package com.caio.ecommerce_project.controller;

import com.caio.ecommerce_project.infraestructure.entitys.Order;
import com.caio.ecommerce_project.infraestructure.entitys.Product;
import com.caio.ecommerce_project.business.AdminService;
import com.caio.ecommerce_project.infraestructure.entitys.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // Ver todos os pedidos (ADMIN)
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(adminService.findAllOrders());
    }

    // CRUD simples de produtos (ADMIN)
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return ResponseEntity.status(201).body(adminService.createProduct(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        return ResponseEntity.ok(adminService.updateProduct(id, product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/findAllUsers")
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.status(200).body(adminService.findAllUsers());
    }
}
