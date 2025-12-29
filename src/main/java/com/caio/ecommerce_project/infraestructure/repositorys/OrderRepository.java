package com.caio.ecommerce_project.infraestructure.repositorys;

import com.caio.ecommerce_project.infraestructure.entitys.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
