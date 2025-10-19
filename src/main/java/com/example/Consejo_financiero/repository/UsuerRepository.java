package com.example.Consejo_financiero.repository;


import com.example.Consejo_financiero.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuerRepository extends JpaRepository<Users, Long> {

    Optional<Users> findById(Long userId);
    Optional<Users> findByName(String UserName);
    Optional<Users> findByEmail(String UserEmail);


    // Método para actualizar solo el balance mínimo
    @Modifying
    @Query("UPDATE Users u SET u.balanceMinimoAlerta = :balanceMinimo WHERE u.userId = :userId")
    void actualizarBalanceMinimo(@Param("userId") Long userId, @Param("balanceMinimo") Double balanceMinimo);


}
