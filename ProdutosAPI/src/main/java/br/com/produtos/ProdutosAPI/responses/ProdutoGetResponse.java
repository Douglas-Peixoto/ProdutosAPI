package br.com.produtos.ProdutosAPI.responses;

import lombok.Data;

@Data
public class ProdutoGetResponse {
    private Integer idProduto;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidade;
    private Double total;
}
