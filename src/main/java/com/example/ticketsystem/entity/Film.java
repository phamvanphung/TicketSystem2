package com.example.ticketsystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "film")
@Accessors(chain = true)
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcType(VarcharJdbcType.class)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name="url_image")
    private String urlImage;

    @Column(name = "url_trailer")
    private String urlTrailer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "price")
    private int price;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "film")
    private List<SuatChieu> suatChieuList;


}
