package com.caio.ecommerce_project.infraestructure.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "email",unique = true)
        private String email;

        @Column(name = "name")
        private String name;

        @Column(name = "password")
        private String password;

        @Column(name = "created_at", updatable = false)
        private Instant createdAt;

        @PrePersist
        public void onPrePersist() {
            if (this.createdAt == null) {
                this.createdAt = Instant.now();
            }
        }
}
