package prominence.divisao7.cash_register.ui.produtos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import prominence.divisao7.cash_register.ui.home.HomeActivity;
import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.common.MenuBottom;
import prominence.divisao7.cash_register.dao.Conexao;
import prominence.divisao7.cash_register.exceptions.NotFoundException;
import prominence.divisao7.cash_register.model.Produto;

public class Detalhes_activity extends AppCompatActivity {

    private Conexao conexao_db;
    private TextView nome_produto;
    private TextView quantidade_produto;
    private TextView preco_produto;
    private TextView prioridade_produto;
    private TextView categorias_produto;
    private CheckBox check_produto;
    private Intent intentRecebida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes);

        inicializar();
        showMenu(R.id.btn_menu);
        voltarHome();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler(Looper.getMainLooper()).post(this::exibirValores);
        new Handler(Looper.getMainLooper()).post(this::marcarCheckProduto);
    }

    //Inicializando variáveis
    private void inicializar() {
        this.conexao_db = Conexao.getInstancia(this);
        this.intentRecebida = getIntent();

        this.nome_produto = findViewById(R.id.nome_produto_detalhe);
        this.quantidade_produto = findViewById(R.id.quantidade_produto_detalhe);
        this.preco_produto = findViewById(R.id.preco_produto_detalhe);
        this.prioridade_produto = findViewById(R.id.prioridade_produto_detalhe);
        this.categorias_produto = findViewById(R.id.categoria_produto_detalhe);
        this.check_produto = findViewById(R.id.checkbox_item_concluido);

        //Setando os dados
        String preco = getString(R.string.valores_preco_uni) + " " + intentRecebida.getStringExtra("preco_produto");
        String quantidade = intentRecebida.getStringExtra("quantidade_produto") + " Un.";

        //Pegando lista de dados para spinner no resources de string
        List<String> lista_prioridades = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.lista_prioridades)));
        String[] string_array_categoria = getResources().getStringArray(R.array.lista_categorias);
        List<String> lista_categorias = new ArrayList<>(Arrays.asList(string_array_categoria));

        this.nome_produto.setText(intentRecebida.getStringExtra("nome_produto"));
        this.quantidade_produto.setText(quantidade);
        this.preco_produto.setText(preco);
        this.prioridade_produto.setText(lista_prioridades.get(intentRecebida.getIntExtra("prioridade_produto", 0)));
        this.categorias_produto.setText(lista_categorias.get(intentRecebida.getIntExtra("categoria_produto", 0)));

        this.check_produto.setChecked(intentRecebida.getBooleanExtra("check_produto", false));
    }


    //Evento de click no btn menu
    private void showMenu(int ID_componente) {
        findViewById(ID_componente).setOnClickListener((e) -> {
            MenuBottom menu = new MenuBottom(Detalhes_activity.this);
            menu.show();
        });
    }


    private void exibirValores() {
        ViewStub stub = findViewById(R.id.viewStud_valores);
        View card_valores_inflado = stub.inflate();

        TextView nome_produto_valores = card_valores_inflado.findViewById(R.id.nome_produto_valores);
        TextView quantidade_produto_valores = card_valores_inflado.findViewById(R.id.quantidade_produto_valores);
        TextView preco_unidade_produto_valores = card_valores_inflado.findViewById(R.id.preco_unidade_produto_valores);
        TextView preco_total_produto_valores = card_valores_inflado.findViewById(R.id.preco_total_produto_valores);

        //Pegando dados no banco


        //Validando se foi encontrado o produto
        Produto produtoEncontrado = conexao_db.produtoRepository().findById(intentRecebida.getIntExtra("id_produto", 0));
        if (produtoEncontrado == null) {
            throw new NotFoundException("Não foi possível encontrar o produto de ID (" + intentRecebida.getIntExtra("id_produto", 0) + ")");
        }

        NumberFormat decimal = new DecimalFormat("0.00");
        String nome = getString(R.string.label_nome) + " " + produtoEncontrado.getNome_produto();
        String quantidade = getString(R.string.label_quantidade) + " " + produtoEncontrado.getQuantidade_produto();
        String preco_unidade = getString(R.string.valores_preco_uni) + " " + decimal.format(produtoEncontrado.getPreco_produto());
        String preco_final = getString(R.string.valores_preco_total) + " " + decimal.format(produtoEncontrado.getPreco_produto() * produtoEncontrado.getQuantidade_produto());


        nome_produto_valores.setText(nome);
        quantidade_produto_valores.setText(quantidade);
        preco_unidade_produto_valores.setText(preco_unidade);
        preco_total_produto_valores.setText(preco_final);
    }


    public void marcarCheckProduto() {
        this.check_produto.setOnClickListener((e) -> {
            conexao_db.produtoRepository().updateCheckProduto(check_produto.isChecked(), intentRecebida.getIntExtra("id_produto", 0));
        });
    }


    //Voltar para a tela principal
    private void voltarHome() {
        findViewById(R.id.btn_voltar_detalhes).setOnClickListener((e) -> {
            finish();
        });
    }


}