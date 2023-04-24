package br.com.produtos.ProdutosAPI.controllers;

import br.com.produtos.ProdutosAPI.entities.Produto;
import br.com.produtos.ProdutosAPI.repositories.ProdutoRepository;
import br.com.produtos.ProdutosAPI.requests.ProdutoPostRequest;
import br.com.produtos.ProdutosAPI.requests.ProdutoPutRequest;
import br.com.produtos.ProdutosAPI.responses.ProdutoGetResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Transactional
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    private static final String ENDPOINT = "/api/produtos";

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity<String> post(@RequestBody ProdutoPostRequest request){
        try {
            Produto produto = new Produto();

            produto.setNome(request.getNome());
            produto.setPreco(request.getPreco());
            produto.setQuantidade(request.getQuantidade());
            produto.setDescricao(request.getDescricao());
            produtoRepository.save(produto);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Produto cadastrado com sucesso!!!");
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
    @CrossOrigin
    public ResponseEntity<String> put(@RequestBody ProdutoPutRequest request){
        try {
            Optional<Produto> item = produtoRepository.findById(request.getIdProduto());

            if (item.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Produto não encontrado");
            }
            else {
                Produto produto = item.get();

                produto.setNome(request.getNome());
                produto.setPreco(request.getPreco());
                produto.setQuantidade(request.getQuantidade());
                produto.setDescricao(request.getDescricao());
                produtoRepository.save(produto);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Produto encontrado com sucesso!!!");
            }
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " +e.getMessage());
        }
    }

    @RequestMapping(value = ENDPOINT + "/{idProduto}", method = RequestMethod.DELETE)
    @CrossOrigin
    public ResponseEntity<String> delete(@PathVariable ("idProduto") Integer idProduto){

        try {
            Optional<Produto> item = produtoRepository.findById(idProduto);

            if (item.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Produto não encontrado");
            }
            else {
                Produto produto = item.get();
                produtoRepository.delete(produto);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Produto deletado com sucesso!!!");
            }
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " +e.getMessage());
        }
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
    @CrossOrigin
    public ResponseEntity<List<ProdutoGetResponse>> get(){
        List<ProdutoGetResponse> response = new ArrayList<ProdutoGetResponse>();
        for (Produto produto : produtoRepository.findAll()){
            ProdutoGetResponse item = new ProdutoGetResponse();

            item.setIdProduto(produto.getIdProduto());
            item.setNome(produto.getNome());
            item.setPreco(produto.getPreco());
            item.setQuantidade(produto.getQuantidade());
            item.setDescricao(produto.getDescricao());
            response.add(item);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @RequestMapping(value = ENDPOINT + "/{idProduto}", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseEntity<ProdutoGetResponse> getById(@PathVariable("idProduto") Integer idProduto){
        Optional<Produto> item = produtoRepository.findById(idProduto);

        if (item.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        else {
            ProdutoGetResponse response = new ProdutoGetResponse();
            Produto produto = item.get();

            response.setIdProduto(produto.getIdProduto());
            response.setNome(produto.getNome());
            response.setPreco(produto.getPreco());
            response.setQuantidade(produto.getQuantidade());
            response.setDescricao(produto.getDescricao());
            response.setTotal(produto.getPreco() * produto.getQuantidade());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
    }
}
