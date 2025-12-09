package com.caio.ecommerce_project.business;

import com.caio.ecommerce_project.infraestructure.entitys.User;
import com.caio.ecommerce_project.infraestructure.repositorys.UserRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user){
        if (repository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email já cadastrado");
        }
        user.setCreatedAt(Instant.now());
        return repository.saveAndFlush(user);
    }

    public User findById(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new RuntimeException("Usuário não encontrado")
        );
    }

    public void deleteByEmail(String email){
        User u = repository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("Usuário não encontrado")
        );
        repository.delete(u);
    }


}
