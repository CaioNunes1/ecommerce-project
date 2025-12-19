package com.caio.ecommerce_project.controller;

import com.caio.ecommerce_project.business.ProductService;
import com.caio.ecommerce_project.infraestructure.entitys.Product;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return ResponseEntity.ok(service.saveProduct(product));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Product>> findAllProducts(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteByEmail(@RequestParam Long id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
