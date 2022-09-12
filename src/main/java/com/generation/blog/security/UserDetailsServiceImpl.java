package com.generation.blog.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import com.generation.blog.model.Usuario;
import com.generation.blog.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	// VALIDA SE O USUÁRIO EXISTE DENTRO DO MEU BANCO DE DADOS por meio do email
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// ver se o email já está cadastrado no banco de dados
	@Override // sobreescrever o que a segurança configura por padrão
				// validar usuário antes de rodar
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
					// ver se o email já existe no meu banco de dados
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(userName);
		
		if (usuario.isPresent()) 
			return new UserDetailsImpl(usuario.get()); // "esse email já está cadastrado"
		else 
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		
	}

}
