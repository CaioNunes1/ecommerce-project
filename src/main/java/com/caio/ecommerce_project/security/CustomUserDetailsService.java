package com.caio.ecommerce_project.security;

import com.caio.ecommerce_project.infraestructure.entitys.User;
import com.caio.ecommerce_project.infraestructure.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        String role = u.getRole() == null ? "USER" : String.valueOf(u.getRole());
        String granted = role.startsWith("ROLE_") ? role : "ROLE_" + role;

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(granted));

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getPassword(),
                authorities
        );
    }
}
