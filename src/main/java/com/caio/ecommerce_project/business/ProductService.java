package com.caio.ecommerce_project.business;

import com.caio.ecommerce_project.infraestructure.entitys.Product;
import com.caio.ecommerce_project.infraestructure.repositorys.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product saveProduct(Product product){
        return repository.saveAndFlush(product);
    }

    public List<Product> findAll(){
        return repository.findAll();
    }


}
