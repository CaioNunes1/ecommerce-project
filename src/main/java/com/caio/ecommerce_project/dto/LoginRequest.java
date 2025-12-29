// src/main/java/com/caio/ecommerce_project/dto/LoginRequest.java
package com.caio.ecommerce_project.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    private String email;
    private String password;
}
