package br.com.produtos.ProdutosAPI.controllers;

import br.com.produtos.ProdutosAPI.entities.Usuario;
import br.com.produtos.ProdutosAPI.repositories.UsuarioRepository;
import br.com.produtos.ProdutosAPI.requests.LoginPostRequest;
import br.com.produtos.ProdutosAPI.security.MD5Cryptography;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Transactional
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String ENDPOINT = "/api/login";

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity<String> post(@RequestBody LoginPostRequest request){
        try {
            Usuario usuario = usuarioRepository.findByLoginAndSenha(request.getLogin(),
                    MD5Cryptography.encrypt(request.getSenha()));

            if (usuario != null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Usuário autenticado com sucesso");
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Acesso negado");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
