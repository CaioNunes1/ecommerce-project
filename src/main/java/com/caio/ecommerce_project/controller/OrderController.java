package com.caio.ecommerce_project.controller;

import com.caio.ecommerce_project.business.OrderService;
import com.caio.ecommerce_project.dto.CreatedOrderRequest;
import com.caio.ecommerce_project.infraestructure.entitys.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody CreatedOrderRequest request){
        Order created = service.createOrder(request);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrders(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Order>> myOrders(Principal principal) {
        // principal.getName() retorna o email se o username for email
        List<Order> orders = service.findByUserEmail(principal.getName());
        return ResponseEntity.ok(orders);
    }
}
