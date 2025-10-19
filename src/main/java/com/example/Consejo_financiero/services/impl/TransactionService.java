package com.example.Consejo_financiero.services.impl;

import com.example.Consejo_financiero.entity.Categories;
import com.example.Consejo_financiero.entity.Transaction;
import com.example.Consejo_financiero.entity.Users;
import com.example.Consejo_financiero.repository.ICategoriaRespository;
import com.example.Consejo_financiero.repository.TransactionRepository;
import com.example.Consejo_financiero.repository.UsuerRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ICategoriaRespository categoriaRespository;

    @Autowired
    private UsuerRepository usuarioRepository;

    public List<Transaction> obtenerTransaccionesPorUsuario(Long usuarioId){
        return transactionRepository.findByUsuarioUserId(usuarioId);
    }

    public Transaction crearTransaccion(Long usuarioId, Long categoriaId, Transaction transaccion){
        Users usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Categories categoria = categoriaRespository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        transaccion.setUsuario(usuario);
        transaccion.setCategoria(categoria);

        return transactionRepository.save(transaccion);
    }

    public Transaction actualizarTransaccion(Long id, Transaction transaccionActualizada){
        Transaction transaccion = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaccion  no encontrada"));

        transaccion.setDescripcion(transaccionActualizada.getDescripcion());
        transaccion.setMonto(transaccionActualizada.getMonto());
        transaccion.setFecha(transaccionActualizada.getFecha());
        transaccion.setTipo(transaccionActualizada.getTipo());

        if (transaccionActualizada.getCategoria() != null){
            transaccion.setCategoria(transaccionActualizada.getCategoria());
        }

        return transactionRepository.save(transaccion);
    }
    public  void eliminarTransaccion (Long id){
        transactionRepository.deleteById(id);
    }

    //Metodo  para hacer los calculos
    public  Double obtenerTotalIngresos (Long usuarioId){
        List<Transaction> ingresos = transactionRepository.findByUsuarioUserIdAndTipo(usuarioId, "INGRESO");
        return ingresos.stream().mapToDouble(Transaction::getMonto).sum();
    }

    public Double obtenerTotalGastos(Long usuarioId){
        List<Transaction> gastos = transactionRepository.findByUsuarioUserIdAndTipo(usuarioId, "GASTO");
        return gastos.stream().mapToDouble(Transaction::getMonto).sum();
    }

    public Double obtenerBalance(Long usuarioId){
        Double ingresos = obtenerTotalIngresos(usuarioId);
        Double gastos = obtenerTotalGastos(usuarioId);
        return ingresos -gastos;
    }

}
