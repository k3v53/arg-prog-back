package com.kevinvicente.argprogback.argprogback.repository;

import com.kevinvicente.argprogback.argprogback.entity.Experiencia;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExperienciaRepository extends JpaRepository<Experiencia, Integer> {
    public Optional<Experiencia> findByTituloEx(String tituloEx);

    public boolean existsByTituloEx(String tituloEx);
}
