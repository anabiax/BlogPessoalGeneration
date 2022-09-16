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

import com.generation.blog.model.Postagem;
import com.generation.blog.repository.PostagemRepository;
import com.generation.blog.repository.TemaRepository;

@RestController //  receberá requisições que serão compostas por: URL, verbo e corpo da requisição
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired 
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){ // retorna todos os Objetos da Classe Postagem persistidos no Banco de dados
		return ResponseEntity.ok(postagemRepository.findAll());
	
	}
	
	@GetMapping("/{id}") // mapeia todas as Requisições HTTP GET, , enviadas para um endereço específico (Endpoint), dentro do Recurso Postagem, para um Método específico que responderá as requisições, ou seja, ele indica que o Método getById
	public ResponseEntity<Postagem> getById(@PathVariable Long id){ // variável do caminho
		return postagemRepository.findById(id)
/*optional*/	.map(resposta -> ResponseEntity.ok(resposta)) //pode devolver tanto um obj ou erro na requisição
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
				// como se fosse uma subrota
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo)); // o parâmetro é o nome da variável
	}
	
	@PostMapping                  //nome do método
	public ResponseEntity<Postagem> post (@Valid @RequestBody Postagem postagem ){
		 if (temaRepository.existsById(postagem.getTema().getId()))
		        return ResponseEntity.status(HttpStatus.CREATED)
		                .body(postagemRepository.save(postagem));

		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();	}
	
	@PutMapping                  
	public ResponseEntity<Postagem> put (@Valid @RequestBody Postagem postagem ){
		if (postagemRepository.existsById(postagem.getId())) {

            if (temaRepository.existsById(postagem.getTema().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(postagemRepository.save(postagem));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
	}
	
    @ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
    	Optional<Postagem> postagem = postagemRepository.findById(id);

        if(postagem.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
		postagemRepository.deleteById(id);
	}
	
}

