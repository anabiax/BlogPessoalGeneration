package com.generation.blog.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blog.model.Usuario;

public class UserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID=1L;
	// id começarão do id 0 do tipo Long
	
	private String userName;
	private String password;
	
	private List<GrantedAuthority> authorities;
	
	// considerando a minha model aqui
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario(); // email 
		this.password = user.getSenha();
	}
	
	// se o usuário quiser fornecer infos além de usuário e senha, tipo foto, ele n será barrado
	public UserDetailsImpl() { }

	// CONFIGURANDO A REGRA DE NEGÓCIO
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// sempre retornará uma List vazia, porquê este Atributo não pode ser Nulo
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
