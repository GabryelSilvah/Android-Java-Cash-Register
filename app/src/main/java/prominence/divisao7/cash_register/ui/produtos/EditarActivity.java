package prominence.divisao7.cash_register.ui.produtos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import prominence.divisao7.cash_register.ui.home.HomeActivity;
import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.common.MenuBottom;
import prominence.divisao7.cash_register.dao.Conexao;
import prominence.divisao7.cash_register.exceptions.DuplicateNameException;
import prominence.divisao7.cash_register.exceptions.BadRequestException;
import prominence.divisao7.cash_register.model.Produto;

public class EditarActivity extends AppCompatActivity {

    private Conexao conexao_db;
    private EditText input_nome;
    private EditText input_quantidade;
    private Spinner input_spinner_prioridade;
    private Spinner input_spinner_categorias;
    private EditText input_preco;
    private Intent intentRecebida;
    private AppCompatButton btn_salva_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        inizialiar();
        showMenu(R.id.btn_menu);
        voltarHome();
    }


    @Override
    protected void onResume() {
        super.onResume();

        this.btn_salva_item.setOnClickListener((e) -> {
            new Handler(Looper.getMainLooper()).post(this::salvar);
        });
    }

    public void inizialiar() {

        this.conexao_db = Conexao.getInstancia(getApplicationContext());
        this.input_nome = findViewById(R.id.input_nome);
        this.input_quantidade = findViewById(R.id.input_quantidade);
        this.input_quantidade = findViewById(R.id.input_quantidade);
        this.input_preco = findViewById(R.id.input_preco);
        this.input_spinner_prioridade = findViewById(R.id.input_spinner_prioridade);
        this.btn_salva_item = findViewById(R.id.btn_salva_item);
        this.input_spinner_categorias = findViewById(R.id.input_spinner_categorias);
        this.intentRecebida = getIntent();


        //Pegando lista de dados para spinner no resources de string
        List<String> lista_prioridades = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.lista_prioridades)));
        ArrayAdapter<String> adapterPrioridade = new ArrayAdapter<>(this, R.layout.molde_lista_spinner, lista_prioridades);
        adapterPrioridade.setDropDownViewResource(R.layout.molde_lista_spinner);
        input_spinner_prioridade.setAdapter(adapterPrioridade);
        input_spinner_prioridade.setSelection(intentRecebida.getIntExtra("prioridade_produto", 0));


        //Pegando lista de dados para spinner no resources de string
        List<String> lista_categorias = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.lista_categorias)));
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(this, R.layout.molde_lista_spinner, lista_categorias);
        adapterCategorias.setDropDownViewResource(R.layout.molde_lista_spinner);
        input_spinner_categorias.setAdapter(adapterCategorias);
        this.input_spinner_categorias.setSelection(intentRecebida.getIntExtra("categoria_produto", 0));


        //Setando no formulário informações já cadastadas
        this.input_nome.setText(intentRecebida.getStringExtra("nome_produto"));
        this.input_quantidade.setText(intentRecebida.getStringExtra("quantidade_produto"));
        this.input_preco.setText(intentRecebida.getStringExtra("preco_produto"));


        //Alterando nome do button de salvar para alterar
        this.btn_salva_item.setText(getString(R.string.btn_alterar));
    }


    //Evento de click no btn menu
    private void showMenu(int ID_componente) {
        findViewById(ID_componente).setOnClickListener((e) -> {
            MenuBottom menu = new MenuBottom(EditarActivity.this);
            menu.show();
        });
    }


    //Voltar a tela principal após alterar produto
    public void voltarHome() {
        findViewById(R.id.btn_cancelar).setOnClickListener((e) -> {
            finish();
        });
    }


    //Salvando alterações
    public void salvar() {
        try {
            //Validando se todos os campos foram preenchidos
            if (input_nome.getText().toString().isEmpty()) {
                throw new BadRequestException(getString(R.string.mensagem_toast_nome_vazio));
            }

            if (input_quantidade.getText().toString().isEmpty()) {
                throw new BadRequestException(getString(R.string.mensagem_toast_quantidade_vazio));
            }


            //Pegando dados
            String nome_produto = input_nome.getText().toString();
            int quantidade_produto = Integer.parseInt(input_quantidade.getText().toString());
            String prioridade_produto = input_spinner_prioridade.getSelectedItem().toString();
            String categoria_produto = (input_spinner_categorias.getSelectedItem() != null) ? input_spinner_categorias.getSelectedItem().toString() : null;
            double preco_produto = (input_preco.getText() != null) ? Double.parseDouble(input_preco.getText().toString()) : 0;


            //Pegando lista de dados para spinner no resources de string
            List<String> lista_prioridades = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.lista_prioridades)));
            int indecePrioridade = lista_prioridades.indexOf(prioridade_produto);

            //Pegando lista de dados para spinner no resources de string
            List<String> lista_categorias = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.lista_categorias)));
            int indeceCategoria = lista_categorias.indexOf(categoria_produto);


            //Validando se nome já existe
            Optional<Produto> produtoEncontrado = conexao_db.produtoRepository().findByName(nome_produto);
            if (
                    produtoEncontrado.isPresent() &&
                            !(produtoEncontrado.get().getId_produto() == intentRecebida.getIntExtra("id_produto", 0))
            ) {
                throw new DuplicateNameException(getString(R.string.mensagem_toast_nome_repetido));
            }

            //Salvando alterações
            Conexao.getInstancia(getApplicationContext());
            conexao_db.produtoRepository().updateProduto(
                    nome_produto,
                    quantidade_produto,
                    indecePrioridade,
                    indeceCategoria,
                    preco_produto,
                    intentRecebida.getIntExtra("id_produto", 0)
            );


            //Menssagem de sucesso e retornar para home
            Toast.makeText(EditarActivity.this, getString(R.string.mensagem_toast_sucesso_editar), Toast.LENGTH_SHORT).show();
            finish();
        } catch (BadRequestException | DuplicateNameException erro) {
            Toast.makeText(EditarActivity.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            conexao_db.close();
        }
    }


}