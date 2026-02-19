package prominence.divisao7.cash_register.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.Optional;

import prominence.divisao7.cash_register.model.Produto;

@Dao
public interface ProdutoRepository {

    @Query("SELECT * FROM produtos")
    List<Produto> findAll();

    @Query("SELECT * FROM produtos WHERE nome_produto = :nome_produto")
    Optional<Produto> findByName(String nome_produto);

    @Query("SELECT * FROM produtos WHERE id_produto = :id")
    Produto findById(int id);

    @Query("SELECT * FROM produtos WHERE prioridade_produto = :prioridade")
    List<Produto> findPrioridade(int prioridade);

    @Query("SELECT * FROM produtos ORDER BY quantidade_produto DESC")
    List<Produto> findOrderMaior();

    @Query("SELECT * FROM produtos ORDER BY quantidade_produto ASC")
    List<Produto> findOrderMenor();

    @Query("SELECT * FROM produtos WHERE check_produto = 0")
    List<Produto> findNotCheck();

    @Query("SELECT * FROM produtos WHERE check_produto = 1")
    List<Produto> findCheck();

    @Query("SELECT COUNT(id_produto) FROM produtos")
    int countProdutos();

    @Query("SELECT SUM((preco_produto)*quantidade_produto) FROM produtos")
    double precoFinalProdutos();

    @Insert
    void save(Produto produto);

    @Query("DELETE FROM produtos WHERE id_produto = :id_produto")
    void delete(int id_produto);

    @Query("UPDATE produtos " +
            "SET " +
            "nome_produto = :nome, " +
            "quantidade_produto = :quantidade, " +
            "prioridade_produto = :prioridade, " +
            "categoria_produto =  :categoria, " +
            "preco_produto = :preco " +
            "WHERE id_produto = :id_produto")
    void updateProduto(String nome, int quantidade, int prioridade, int categoria, double preco, int id_produto);


    @Query("UPDATE produtos SET check_produto = :check_produto WHERE id_produto = :id_produto")
    void updateCheckProduto(boolean check_produto, int id_produto);
}