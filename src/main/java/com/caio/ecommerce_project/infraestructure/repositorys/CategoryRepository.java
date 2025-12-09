package com.caio.ecommerce_project.infraestructure.repositorys;

import com.caio.ecommerce_project.infraestructure.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
