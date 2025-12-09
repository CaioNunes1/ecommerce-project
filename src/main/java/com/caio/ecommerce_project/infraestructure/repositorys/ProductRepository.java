package com.caio.ecommerce_project.infraestructure.repositorys;

import com.caio.ecommerce_project.infraestructure.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
//jpa é o facilitador passando o nome da tabela o product e o tipo que está o id em product
    //jpa tem metodos prontos de salvar, pegar por id e esse tipo de coisa
}
