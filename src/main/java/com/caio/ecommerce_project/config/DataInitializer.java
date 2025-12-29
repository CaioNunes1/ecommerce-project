package com.caio.ecommerce_project.config;

import com.caio.ecommerce_project.infraestructure.entitys.Role;
import com.caio.ecommerce_project.infraestructure.entitys.User;
import com.caio.ecommerce_project.infraestructure.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@local")) {
            User admin = User.builder()
                    .email("admin@local")
                    .name("Admin")
                    .password(passwordEncoder.encode("admin123"))
                    .createdAt(Instant.now())
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.saveAndFlush(admin);
            System.out.println("Admin user created: admin@local / admin123");
        }
    }
}
