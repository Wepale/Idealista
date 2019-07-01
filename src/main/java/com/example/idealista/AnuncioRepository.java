package com.example.idealista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
}
