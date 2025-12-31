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

    public Product findById(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new RuntimeException("Produto não encontrado")
        );
    }

    public void deleteProduct(Long id){
        Product product = repository.findById(id).orElseThrow(
                ()-> new RuntimeException("Produto não encontrado")
        );
        repository.delete(product);
    }

    public Product uptadeProduct(Product updated){
        Product product = findById(updated.getId());
        product.setName(updated.getName());
        product.setPrice(updated.getPrice());
        product.setCategory(updated.getCategory());
        return repository.saveAndFlush(product);

    }


}
