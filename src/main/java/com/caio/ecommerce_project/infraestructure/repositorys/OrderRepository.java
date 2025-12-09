package com.caio.ecommerce_project.infraestructure.repositorys;

import com.caio.ecommerce_project.infraestructure.entitys.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
