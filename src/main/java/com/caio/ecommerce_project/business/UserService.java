package com.caio.ecommerce_project.business;

import com.caio.ecommerce_project.infraestructure.entitys.Role;
import com.caio.ecommerce_project.infraestructure.entitys.User;
import com.caio.ecommerce_project.infraestructure.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user){
        if (repository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email já cadastrado");
        }
        // atribui role padrão
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole(Role.valueOf("USER"));
        }
        // hash da senha
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setCreatedAt(Instant.now());
        return repository.saveAndFlush(user);
    }


    public User authenticate(String email, String rawPassword){
        User user = repository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("Usuário não encontrado")
        );
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }
        return user;
    }

    public User findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("Usuário não encontrado")
        );

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
