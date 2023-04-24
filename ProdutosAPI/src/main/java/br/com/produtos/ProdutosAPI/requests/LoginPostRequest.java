package br.com.produtos.ProdutosAPI.requests;

import lombok.Data;

@Data
public class LoginPostRequest {
    private String login;
    private String senha;
}
