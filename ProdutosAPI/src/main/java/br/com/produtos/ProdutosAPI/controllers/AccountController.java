package br.com.produtos.ProdutosAPI.controllers;

import br.com.produtos.ProdutosAPI.entities.Usuario;
import br.com.produtos.ProdutosAPI.repositories.UsuarioRepository;
import br.com.produtos.ProdutosAPI.requests.AccountPostRequest;
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
public class AccountController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String ENDPOINT = "/api/account";

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity<String> post(@RequestBody AccountPostRequest request){
        try {
            if (usuarioRepository.findByLogin(request.getLogin())!=null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O login informado ja esta cadastrado no sistema, tente outro.");
            }

            Usuario usuario = new Usuario();

            usuario.setNome(request.getNome());
            usuario.setLogin(request.getLogin());
            usuario.setSenha(MD5Cryptography.encrypt(request.getSenha()));
            usuarioRepository.save(usuario);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Conta criada com sucesso!!!");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
