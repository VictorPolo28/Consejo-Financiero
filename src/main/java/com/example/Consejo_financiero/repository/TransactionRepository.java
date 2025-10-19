package com.example.Consejo_financiero.repository;

import com.example.Consejo_financiero.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Encontrar transacciones por usuario
    List<Transaction> findByUsuarioUserId(Long usuarioId);

    // Encontrar transacciones por usuario y tipo
    List<Transaction> findByUsuarioUserIdAndTipo(Long usuarioId, String tipo);

    // Encontrar transacciones por categoría
    List<Transaction> findByCategoriaId(Long categoriaId);

    // Encontrar transacciones por rango de fechas
    List<Transaction> findByUsuarioUserIdAndFechaBetween(Long usuarioId, Date startDate, Date endDate);
}
