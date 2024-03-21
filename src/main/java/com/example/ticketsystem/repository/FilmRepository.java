package com.example.ticketsystem.repository;

import com.example.ticketsystem.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilmRepository extends JpaRepository<Film, UUID> {
    Optional<Film> findByName(String name);
}
