package com.kevinvicente.argprogback.argprogback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kevinvicente.argprogback.argprogback.entity.Educacion;

@Repository
public interface IEducacionRepository extends JpaRepository<Educacion, Integer> {
    public Optional<Educacion> findByTituloEd(String tituloEd);

    public boolean existsByTituloEd(String tituloEd);
}
