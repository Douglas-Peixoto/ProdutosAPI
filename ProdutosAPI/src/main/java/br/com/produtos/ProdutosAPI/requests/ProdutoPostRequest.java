package br.com.produtos.ProdutosAPI.requests;

import lombok.Data;

@Data
public class ProdutoPostRequest {
    private String nome;
    private Double preco;
    private Integer quantidade;
    private String descricao;
}
