package com.generation.blog.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  // vai transformar o obj em uma entidade, ou seja, tabela dentro do banco de dados.
@Table(name = "tb_tema")
public class Tema {
	
	// construção do objeto
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull   // p/ deixar o campo obrigatório
	private String descricao;
	
/* e bom ter em mente que nas anotacoes tipo OneToMany, a primeira palavra One vai sempre se referir a classe que foi inserida. 
 * Ou seja One dessa classe aqui to many da outra classe. E na postagem foi ManyToOne ou seja, many dessa classe postagem to One da outra classe  */	
	
// estabelece relação e é dependente
								// efeito cascada íntegra = se eu mudar o tema irá alterar toda as postagens sofrerão mudanças 
	@OneToMany(mappedBy = "tema", cascade = CascadeType.ALL) // atributo tema da tabela de postagem
	@JsonIgnoreProperties("tema")  // obj de tema está atrelado ao de tema e vice-versa. um pertence ao outro. isso é recursividade - gera um efeito de loop eterno, mas com Json trava a aplicação assim que pega o que quer.
	private List<Postagem> postagem;
	    // definição de que várias postagens serão exibidas numa lista
		// precisa ser obrigatoriamente um array
	
	// get e set - manipular os dados dentro da tabela
	// tem que ter todos, caso contrário quebrará a regra de negócio do código 
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<Postagem> getPostagem() {
		return postagem;
	}
	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

	
}
