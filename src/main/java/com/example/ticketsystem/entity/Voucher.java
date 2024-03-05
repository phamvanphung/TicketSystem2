package com.example.ticketsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "voucher")
@Setter
@Getter
public class Voucher {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcType(VarcharJdbcType.class)
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "expired")
    private LocalDateTime expired;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "start")
    private LocalDateTime start;
    @Column(name = "discount")
    private int discount;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "code")
    private String code;
    @Column(name = "used")
    private boolean used = false;


    @OneToOne
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    private Order order;


}
