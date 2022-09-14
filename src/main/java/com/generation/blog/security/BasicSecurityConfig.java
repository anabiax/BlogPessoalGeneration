package com.generation.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {
	
	 // funciona em escopo global
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean // definindo as "permissões" até onde o usuário poderá avançar
		// regras de negócio
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable() // estou desabilitando o automático do put e delete. Por padrão a segurança barra
            .cors(); // evita bloqueio de acesso de portas externas

        http
            .authorizeHttpRequests((auth) -> auth
                .antMatchers("/usuarios/logar").permitAll()
                .antMatchers("/usuarios/cadastrar").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll() // se eu n quiser que o usuário exclua algo devo modificar aqui
                .anyRequest().authenticated()) // tudo será mediante autenticação!!!
            .httpBasic();

        return http.build(); // pegar todas as regrinhas configuradas acima substituir a segurança padrão 

    }
}
