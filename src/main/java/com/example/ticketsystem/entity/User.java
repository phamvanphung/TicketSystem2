package com.example.ticketsystem.entity;


import com.example.ticketsystem.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcType(VarcharJdbcType.class)
    private UUID id;

    @Column(name = "fullname")
    private String fullname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "dob")
    private LocalDateTime dob;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "inactive")
    private boolean inactive;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

}
