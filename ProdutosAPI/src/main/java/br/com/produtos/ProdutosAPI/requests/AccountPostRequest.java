package br.com.produtos.ProdutosAPI.requests;

import lombok.Data;

@Data
public class AccountPostRequest {
    private String nome;
    private String login;
    private String senha;
}
