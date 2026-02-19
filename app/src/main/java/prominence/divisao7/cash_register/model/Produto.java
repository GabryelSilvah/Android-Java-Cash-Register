package prominence.divisao7.cash_register.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "produtos")
public class Produto {

    @ColumnInfo(name = "id_produto")
    @PrimaryKey(autoGenerate = true)
    private int id_produto;

    @ColumnInfo(name = "nome_produto")
    private String nome_produto;

    @ColumnInfo(name = "quantidade_produto")
    private int quantidade_produto;

    @ColumnInfo(name = "prioridade_produto")
    private int prioridade_produto;

    @ColumnInfo(name = "categoria_produto")
    private int categoria_produto;

    @ColumnInfo(name = "preco_produto")
    private double preco_produto;

    @ColumnInfo(name = "check_produto")
    private boolean check_produto;

    //Construtor
    public Produto(String nome_produto, int quantidade_produto, int prioridade_produto, int categoria_produto, double preco_produto, boolean check_produto) {
        this.nome_produto = nome_produto;
        this.quantidade_produto = quantidade_produto;
        this.prioridade_produto = prioridade_produto;
        this.categoria_produto = categoria_produto;
        this.preco_produto = preco_produto;
        this.check_produto = check_produto;
    }


    //Gets e sets
    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public int getQuantidade_produto() {
        return quantidade_produto;
    }

    public void setQuantidade_produto(int quantidade_produto) {
        this.quantidade_produto = quantidade_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public int getPrioridade_produto() {
        return prioridade_produto;
    }

    public void setPrioridade_produto(int prioridade_produto) {
        this.prioridade_produto = prioridade_produto;
    }

    public int getCategoria_produto() {
        return categoria_produto;
    }

    public void setCategoria_produto(int categoria_produto) {
        this.categoria_produto = categoria_produto;
    }

    public double getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(double preco_produto) {
        this.preco_produto = preco_produto;
    }

    public boolean getCheck_produto() {
        return check_produto;
    }

    public void setCheck_produto(boolean check_produto) {
        this.check_produto = check_produto;
    }
}
