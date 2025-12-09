package com.caio.ecommerce_project.infraestructure.repositorys;

import com.caio.ecommerce_project.infraestructure.entitys.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemIntemRepository extends JpaRepository<OrderItem,Long> {
}
