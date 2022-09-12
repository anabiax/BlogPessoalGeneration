package com.generation.blog.service;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blog.model.Usuario;
import com.generation.blog.model.UsuarioLogin;
import com.generation.blog.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository usuarioRepository;
	
    private String criptografarSenha(String senha) {

    	// semelhante ao Scanner() do Java
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // usando o método do bcrypt que criptografa a senha digitada e retorna a senha já criptografada
        return encoder.encode(senha);

    }

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

    	// verificar se o usuário está cadastrado já
        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
            return Optional.empty();

        usuario.setSenha(criptografarSenha(usuario.getSenha())); // criptografando a senha
        	// inserindo dentro do campo de senha
        
        // manda o obj de usuário p/ o banco de dados
        return Optional.of(usuarioRepository.save(usuario));
    
    }
    

    public Optional<Usuario> atualizarUsuario(Usuario usuario) {
        
    	// verificando se o usuário já existe pelo id, por ser unico e intransferível
    	// tem que existir p/ ser atualizado
        if(usuarioRepository.findById(usuario.getId()).isPresent()) {

            Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

            if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            usuario.setSenha(criptografarSenha(usuario.getSenha()));

            // n consigo mandar s/ usuário e/ou senha - são obrigatórios.
            return Optional.ofNullable(usuarioRepository.save(usuario));
            
        }

        return Optional.empty();
    
    }   

    public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

        Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

        if (usuario.isPresent()) {

            if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(),        usuarioLogin.get().getSenha()));
                usuarioLogin.get().setSenha(usuario.get().getSenha());

                return usuarioLogin;

            }
        }   

        return Optional.empty();
        
    }
    
    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        return encoder.matches(senhaDigitada, senhaBanco);

    }

    private String gerarBasicToken(String usuario, String senha) {

        String token = usuario + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);

    }
	
}
