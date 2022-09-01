package com.generation.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blog.model.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long> {
	
// retorna uma lista do tipo Tema    // nome da lista
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);

}
