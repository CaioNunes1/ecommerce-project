package com.caio.ecommerce_project.business;

import com.caio.ecommerce_project.infraestructure.entitys.Category;
import com.caio.ecommerce_project.infraestructure.repositorys.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Category createCategory(Category category){
        return repository.saveAndFlush(category);
    }
    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Integer id) {
        return repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Category update(Integer id, Category updated) {
        Category category = findById(id);
        category.setName(updated.getName());
        return repository.save(category);
    }

    public void delete(Integer id) {
        repository.deleteById(Long.valueOf(id));
    }
}
