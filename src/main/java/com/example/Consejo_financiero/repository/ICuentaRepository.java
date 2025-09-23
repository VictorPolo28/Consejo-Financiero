package com.example.Consejo_financiero.repository;

import com.example.Consejo_financiero.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("SELECT c FROM Cuenta c WHERE c.usuario.userId = :usuarioId")
    List<Cuenta> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}
