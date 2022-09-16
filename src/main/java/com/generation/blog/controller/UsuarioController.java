package com.generation.blog.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blog.model.Usuario;
import com.generation.blog.repository.UsuarioRepository;
import com.generation.blog.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAll(){
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById (@PathVariable Long id){
		return usuarioRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/usuario/{usuario}")                                                       // CONFERIR
	public ResponseEntity<Optional<Usuario>> getByUsuario(@PathVariable String usuario){
		return ResponseEntity.ok(usuarioRepository.findByUsuario(usuario));
	}
	
	@PostMapping
	public ResponseEntity<Usuario> post (@Valid @RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioRepository.save(usuario));
	}
	
	@PutMapping
	public ResponseEntity<Usuario> put (@Valid @RequestBody Usuario usuario){
		return ResponseEntity.ok(usuarioRepository.save(usuario));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping ("{id}")
	public void delete(@PathVariable Long id) {
		Optional <Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		usuarioRepository.deleteById(id);
	}
	
}
