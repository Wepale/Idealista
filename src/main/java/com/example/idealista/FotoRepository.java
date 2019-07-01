package com.example.idealista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface FotoRepository extends JpaRepository<Foto, Long> {
}
