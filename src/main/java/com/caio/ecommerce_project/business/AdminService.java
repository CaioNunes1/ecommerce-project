package com.caio.ecommerce_project.business;

import com.caio.ecommerce_project.infraestructure.entitys.Order;
import com.caio.ecommerce_project.infraestructure.entitys.Product;
import com.caio.ecommerce_project.infraestructure.entitys.User;
import com.caio.ecommerce_project.infraestructure.repositorys.OrderRepository;
import com.caio.ecommerce_project.infraestructure.repositorys.ProductRepository;
import com.caio.ecommerce_project.infraestructure.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Product createProduct(Product p) {
        return productRepository.saveAndFlush(p);
    }

    public Product updateProduct(Long id, Product newP) {
        Product p = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        p.setName(newP.getName());
        p.setSku(newP.getSku());
        p.setPrice(newP.getPrice());
        p.setDescription(newP.getDescription());
        // se tiver categoria, trate relacionamento aqui
        return productRepository.saveAndFlush(p);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
