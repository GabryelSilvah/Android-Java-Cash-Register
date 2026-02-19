package prominence.divisao7.cash_register.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.adapter.AdapterProduto;
import prominence.divisao7.cash_register.common.MenuBottom;
import prominence.divisao7.cash_register.common.Recurso;
import prominence.divisao7.cash_register.dao.Conexao;
import prominence.divisao7.cash_register.model.Produto;
import prominence.divisao7.cash_register.ui.produtos.Cadastro_Activity;


public class HomeActivity extends AppCompatActivity {

    private LinearLayout container_lista_produtos;
    private Spinner input_spinner_filtros_produtos;
    private RecyclerView recycler;
    private AdapterProduto adapterProduto;
    private Conexao conexao_db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        inicializar();
        showMenu(R.id.btn_menu);
        chamarCadastro();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler(Looper.getMainLooper()).post(this::listarProdutos);
        new Handler(Looper.getMainLooper()).post(this::aplicarFiltro);
    }

    //Carregando configurações essênciais
    private void inicializar() {
        this.conexao_db = Conexao.getInstancia(this);
        this.container_lista_produtos = findViewById(R.id.container_lista_produtos);
        this.input_spinner_filtros_produtos = findViewById(R.id.input_spinner_filtros_produtos);
        this.conexao_db = Conexao.getInstancia(this);


        //Pegando lista com nome dos filtros dos produtos
        List<String> lista_nomeFiltros_produtos = Arrays.asList(getResources().getStringArray(R.array.lista_nomeFiltros_produtos));


        //Setando nome dos filtros no molde de spinner customizado
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, R.layout.molde_lista_spinner, lista_nomeFiltros_produtos);
        adapterSpinner.setDropDownViewResource(R.layout.molde_lista_spinner);
        this.input_spinner_filtros_produtos.setAdapter(adapterSpinner);
        this.input_spinner_filtros_produtos.setSelection(0);//Item padrão


        //Inserindo recyclerView no LinearLayout da tela valores
        container_lista_produtos.addView(LayoutInflater.from(this).inflate(R.layout.recycler_add, container_lista_produtos, false));
        this.recycler = findViewById(R.id.lista_recycler_produtos);
    }


    //Evento de click no btn menu
    private void showMenu(int ID_componente) {
        findViewById(ID_componente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBottom menu = new MenuBottom(HomeActivity.this);
                menu.show();
            }
        });
    }


    //Chamando tela de cadastro
    private void chamarCadastro() {
        findViewById(R.id.btn_add_novo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Cadastro_Activity.class);
                startActivity(intent);
            }
        });

    }


    //Listando produtos com recyclerView
    private void listarProdutos() {

        //Buscando dados
        List<Produto> listaProdutos = conexao_db.produtoRepository().findAll();

        //Validando se a lista está vazia
        if (listaProdutos.isEmpty()) {
            recycler.setAdapter(null);
            Recurso.exibirMensagemNotFound(container_lista_produtos, HomeActivity.this,getString(R.string.mensagem_lista_vazia));
            return;
        }

        //Configurando e exibindo lista na recyclerView
        adapterProduto = new AdapterProduto(listaProdutos, this);
        this.recycler.setAdapter(adapterProduto);

    }


    //Aplicando pesquisar por filtro
    private void aplicarFiltro() {

        input_spinner_filtros_produtos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                List<Produto> listaProdutos = new ArrayList<>();

                //Chamando método de busca de acordo com filtro setado
                String[] string_array_prioridades = getResources().getStringArray(R.array.lista_prioridades);
                List<String> lista_prioridades = new ArrayList<>(Arrays.asList(string_array_prioridades));
                switch (position) {
                    case 0:
                        listaProdutos = conexao_db.produtoRepository().findAll();
                        break;
                    case 1:
                        listaProdutos = conexao_db.produtoRepository().findOrderMaior();
                        break;
                    case 2:
                        listaProdutos = conexao_db.produtoRepository().findOrderMenor();
                        break;
                    case 3:
                        int indecePrioridadeBaixa = lista_prioridades.indexOf(lista_prioridades.get(0));
                        listaProdutos = conexao_db.produtoRepository().findPrioridade(indecePrioridadeBaixa);
                        break;
                    case 4:
                        int indecePrioridadeMedia = lista_prioridades.indexOf(lista_prioridades.get(1));
                        listaProdutos = conexao_db.produtoRepository().findPrioridade(indecePrioridadeMedia);
                        break;
                    case 5:
                        int indecePrioridadeAlta = lista_prioridades.indexOf(lista_prioridades.get(2));
                        listaProdutos = conexao_db.produtoRepository().findPrioridade(indecePrioridadeAlta);
                        break;
                    case 6:
                        listaProdutos = conexao_db.produtoRepository().findCheck();
                        break;
                    case 7:
                        listaProdutos = conexao_db.produtoRepository().findNotCheck();
                        break;
                    default:
                        recycler.setAdapter(null);
                        Recurso.exibirMensagemNotFound(container_lista_produtos, HomeActivity.this,getString(R.string.mensagem_erro_filtragem));
                        break;

                }


                //Validando se a lista está vazia
                if (listaProdutos.isEmpty() && position != 0) {
                    recycler.setAdapter(null);
                    Recurso.exibirMensagemNotFound(container_lista_produtos, HomeActivity.this,getString(R.string.mensagem_lista_filtragem_vazia));
                    return;
                }


                if (!listaProdutos.isEmpty()) {
                    //Removendo toda e qualquer view que tenha sido adicionada antes dentro do container central, para remover TextView com mensagem
                    container_lista_produtos.removeAllViews();

                    //Adicionando e vinculando listaRecycler novamento porque pode ter sido removida quando não encontrou algum produto na filtragem
                    container_lista_produtos.addView(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_add, container_lista_produtos, false));
                    recycler = findViewById(R.id.lista_recycler_produtos);


                    //Configurando e exibindo lista na recyclerView
                    adapterProduto = new AdapterProduto(listaProdutos, HomeActivity.this);
                    recycler.setAdapter(adapterProduto);
                    return;
                }

                recycler.setAdapter(null);
                Recurso.exibirMensagemNotFound(container_lista_produtos, HomeActivity.this, getString(R.string.mensagem_lista_vazia));

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




}
