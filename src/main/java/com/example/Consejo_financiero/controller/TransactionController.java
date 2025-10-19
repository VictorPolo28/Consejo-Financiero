package com.example.Consejo_financiero.controller;


import com.example.Consejo_financiero.entity.Transaction;
import com.example.Consejo_financiero.services.impl.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin("http://localhost:3000")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/usuario/{usuarioId}")
    public List<Transaction> obtenerTransacciones(@PathVariable Long usuarioId){
        return transactionService.obtenerTransaccionesPorUsuario(usuarioId);
    }

    @PostMapping("/usuario/{usuarioId}/categoria/{categoriaId}")
    public  Transaction crearTransaccion(
            @PathVariable  Long usuarioId,
            @PathVariable Long categoriaId,
            @RequestBody Transaction transaccion){
        return transactionService.crearTransaccion(usuarioId,categoriaId,transaccion);
    }

    @PutMapping("/{id}")
    public Transaction actualizarTransaccion(
            @PathVariable Long id,
            @RequestBody Transaction transaccion){
        return actualizarTransaccion(id, transaccion);
    }

    @DeleteMapping("/{id}")
    public void eliminarTransaccion(
            @PathVariable Long id){
        transactionService.eliminarTransaccion(id);
    }

    //resumen financiero
    @GetMapping("/usuario/{usuarioId}/resumen")
    public Map<String, Double> obtenerResumen(@PathVariable Long usuarioId){
        Map<String, Double> resumen = new HashMap<>();
        resumen.put("totalIngreso",transactionService.obtenerTotalIngresos(usuarioId));
        resumen.put("totalGastos",transactionService.obtenerTotalGastos(usuarioId));
        resumen.put("balance",transactionService.obtenerBalance(usuarioId));

        return resumen;
    }
}
