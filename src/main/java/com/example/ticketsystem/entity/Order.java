package com.example.ticketsystem.entity;


import com.example.ticketsystem.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
@Accessors(chain = true)
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcType(VarcharJdbcType.class)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    private Film film;

    @Column(name = "quality")
    private int quality;
    @Column(name = "total")
    private Long total;

    @Column(name = "status_order")
    @Enumerated(value = EnumType.STRING)
    private StatusOrder statusOrder;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @OneToOne
    private Transaction transaction;
}
