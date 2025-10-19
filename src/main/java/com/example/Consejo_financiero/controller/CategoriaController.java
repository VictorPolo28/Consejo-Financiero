package com.example.Consejo_financiero.controller;

import com.example.Consejo_financiero.entity.Categories;
import com.example.Consejo_financiero.services.impl.CategoriesServicesImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoriaController {

    private final CategoriesServicesImpl categoriaService;

    public CategoriaController(CategoriesServicesImpl categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Long usuarioId) {
        try {
            System.out.println(" Request recibido para categorías del usuario: " + usuarioId);
            List<Categories> categorias = categoriaService.listarPorUsuario(usuarioId);
            System.out.println("Enviando " + categorias.size() + " categorías");
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            System.out.println(" Error en listarPorUsuario: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> crearCategoria(@PathVariable Long usuarioId, @RequestBody Categories categoria) {
        try {
            Categories nuevaCategoria = categoriaService.crear(usuarioId, categoria);
            return ResponseEntity.ok(nuevaCategoria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody Categories categoria) {
        try {
            Categories categoriaActualizada = categoriaService.actualizar(id, categoria);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}