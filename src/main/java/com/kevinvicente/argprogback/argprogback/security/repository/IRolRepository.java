package com.kevinvicente.argprogback.argprogback.security.repository;

import com.kevinvicente.argprogback.argprogback.security.entity.Rol;
import com.kevinvicente.argprogback.argprogback.security.enums.RolNombre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}