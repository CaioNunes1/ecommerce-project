package com.caio.ecommerce_project.controller;

import com.caio.ecommerce_project.business.CategoryService;
import com.caio.ecommerce_project.infraestructure.entitys.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping("/create")
    public ResponseEntity<Category> create(@RequestBody Category category){
        return ResponseEntity.ok(service.createCategory(category));
    }

    @GetMapping("/getAllCategorys")
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestParam Integer id,@RequestBody Category category){
        return ResponseEntity.ok(service.update(id,category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
