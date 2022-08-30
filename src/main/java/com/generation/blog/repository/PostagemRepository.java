package com.generation.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.blog.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
// Uma interface em Java é uma Classe Abstrata (uma Classe que serve de modelo para outras Classes), composta somente por Métodos abstratos. 
	
}
