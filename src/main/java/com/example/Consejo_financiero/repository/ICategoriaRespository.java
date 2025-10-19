package com.example.Consejo_financiero.repository;

import com.example.Consejo_financiero.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoriaRespository extends JpaRepository <Categories, Long> {
    List<Categories> findByUsuarioUserId(Long userId);
}
