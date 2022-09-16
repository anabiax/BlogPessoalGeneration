package com.generation.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.blog.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Optional<Usuario> findByUsuario(String usuario);
		// irá checar se o usuário existe ou não no banco de dados.
	// encontre O USUARIO n tudo (all) 
	
	public List <Usuario> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);	
}
