package org.example.entity;

import lombok.*;
import org.example.entity.enums.Role;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String fullAddress;
    private Role role;

    private Double balance;
}