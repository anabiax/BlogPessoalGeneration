package com.generation.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.blog.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
	
// retorna uma lista do tipo Tema    // nome da lista
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);

}
