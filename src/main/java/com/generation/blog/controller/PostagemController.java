package com.generation.blog.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.generation.blog.model.Postagem;
import com.generation.blog.repository.PostagemRepository;

@RestController //  receberá requisições que serão compostas por: URL, verbo e corpo da requisição
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class PostagemController {

	@Autowired
	private PostagemRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){ // retorna todos os Objetos da Classe Postagem persistidos no Banco de dados
		return ResponseEntity.ok(repository.findAll());
	
	}
	
	@GetMapping("/{id}") // mapeia todas as Requisições HTTP GET, , enviadas para um endereço específico (Endpoint), dentro do Recurso Postagem, para um Método específico que responderá as requisições, ou seja, ele indica que o Método getById
	public ResponseEntity<Postagem> getById(@PathVariable Long id){ // variável do caminho
		return repository.findById(id)
/*optional*/	.map(resposta -> ResponseEntity.ok(resposta)) //pode devolver tanto um obj ou erro na requisição
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
				// como se fosse uma subrota
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo)); // o parâmetro é o nome da variável
	}
	
	@PostMapping                  //nome do método
	public ResponseEntity<Postagem> post (@Valid @RequestBody Postagem postagem ){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@PutMapping                  
	public ResponseEntity<Postagem> put (@Valid @RequestBody Postagem postagem ){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
}

