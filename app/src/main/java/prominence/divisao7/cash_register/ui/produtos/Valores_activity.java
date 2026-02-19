package prominence.divisao7.cash_register.ui.produtos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.adapter.AdapterValores;
import prominence.divisao7.cash_register.common.MenuBottom;
import prominence.divisao7.cash_register.dao.Conexao;
import prominence.divisao7.cash_register.model.Produto;

public class Valores_activity extends AppCompatActivity {

    private TextView txt_preco_final;
    private TextView txt_quantitativo_itens;
    private RecyclerView recyclerValores;
    private LinearLayout container_lista_valores;
    private View recycler_valores_view;
    private Conexao conexao_db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valores);

        inicializar();
        showMenu(R.id.btn_menu);
        inforTopTela();
        listarProdutos();
    }


    private void inicializar() {
        this.conexao_db = Conexao.getInstancia(this);
        this.txt_preco_final = findViewById(R.id.txt_preco_final);
        this.txt_quantitativo_itens = findViewById(R.id.txt_quantitativo_produto);
        this.container_lista_valores = findViewById(R.id.container_lista_valores);

        //Inserindo recyclerView no LinearLayout da tela valores
        //Pegando ID do recyclerView recém inserido
        container_lista_valores.addView(LayoutInflater.from(this).inflate(R.layout.recycler_valores, container_lista_valores, false));
        this.recyclerValores = findViewById(R.id.lista_recycler_valores);
    }


    //Evento de click no btn menu
    private void showMenu(int ID_componente) {
        findViewById(ID_componente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBottom menu = new MenuBottom(Valores_activity.this);
                menu.show();
            }
        });
    }


    private void inforTopTela() {

        NumberFormat decimal = new DecimalFormat("0.00");

        String preco_final_produtos = getString(R.string.valores_montante) + " " + decimal.format(conexao_db.produtoRepository().precoFinalProdutos());
        String quantidade_produtos = getString(R.string.valores_quantidade_itens) + " " + conexao_db.produtoRepository().countProdutos();

        this.txt_preco_final.setText(preco_final_produtos);

        this.txt_quantitativo_itens.setText(quantidade_produtos);


    }


    //Listando produtos com recyclerView
    private void listarProdutos() {

        //Buscando dados
        List<Produto> listaProdutos = conexao_db.produtoRepository().findAll();


        //Validando se a lista está vazia
        if (listaProdutos.isEmpty()) {

            TextView mensagem = new TextView(this);
            mensagem.setText(getString(R.string.mensagem_lista_vazia));

            this.container_lista_valores.addView(mensagem);

        } else {
            //Configurando e exibindo lista na recyclerView
            AdapterValores adapter = new AdapterValores(listaProdutos, this);
            this.recyclerValores.setAdapter(adapter);
        }

        conexao_db.close();
    }
}