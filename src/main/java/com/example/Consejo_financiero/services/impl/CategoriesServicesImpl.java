package com.example.Consejo_financiero.services.impl;



import com.example.Consejo_financiero.entity.Categories;
import com.example.Consejo_financiero.entity.Users;
import com.example.Consejo_financiero.repository.ICategoriaRespository;
import com.example.Consejo_financiero.repository.UsuerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServicesImpl {

    @Autowired
    private ICategoriaRespository categoriesRepository;

    @Autowired
    private UsuerRepository usuarioRepo;



    public CategoriesServicesImpl(ICategoriaRespository categoriaRepo, UsuerRepository usuarioRepo) {
        this.categoriesRepository = categoriaRepo; // Corregido: usa categoriesRepository
        this.usuarioRepo = usuarioRepo;
    }

    // MANTÉN solo este método
    public List<Categories> listarPorUsuario(Long userId){
        System.out.println("🔍 Buscando categorías para usuario ID: " + userId);
        List<Categories> categorias = categoriesRepository.findByUsuarioUserId(userId);
        System.out.println("✅ Categorías encontradas: " + categorias.size());
        return categorias;
    }

    public Categories crear(Long userId, Categories categoria){
        Users usuario = usuarioRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        categoria.setUsuario(usuario);
        return categoriesRepository.save(categoria);
    }

    public Categories actualizar(Long id, Categories categoriaNueva) {
        Categories categoria = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        categoria.setNombreCategoria(categoriaNueva.getNombreCategoria());
        categoria.setDescription(categoriaNueva.getDescription());
        return categoriesRepository.save(categoria);
    }

    public void eliminar(Long id){
        categoriesRepository.deleteById(id);
    }
}