package prominence.divisao7.cash_register.ui.produtos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.common.MenuBottom;
import prominence.divisao7.cash_register.dao.Conexao;
import prominence.divisao7.cash_register.exceptions.DuplicateNameException;
import prominence.divisao7.cash_register.exceptions.BadRequestException;
import prominence.divisao7.cash_register.model.Produto;

public class Cadastro_Activity extends AppCompatActivity {

    private Conexao conexao_db;
    private EditText input_nome;
    private EditText input_quantidade;
    private Spinner input_spinner_prioridade;
    private Spinner input_spinner_categorias;
    private EditText input_preco;

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

        //Salvar novo item
        findViewById(R.id.btn_salva_item).setOnClickListener((e) -> {
            new Handler(Looper.getMainLooper()).post(this::salvar);
        });

    }

    public void inizialiar() {
        this.conexao_db = Conexao.getInstancia(getApplicationContext());
        this.input_nome = findViewById(R.id.input_nome);
        this.input_quantidade = findViewById(R.id.input_quantidade);
        this.input_preco = findViewById(R.id.input_preco);
        this.input_spinner_prioridade = findViewById(R.id.input_spinner_prioridade);
        this.input_spinner_categorias = findViewById(R.id.input_spinner_categorias);


        //Bucando lista de dados para spinner no resources de string
        List<String> lista_prioridades = Arrays.asList(getResources().getStringArray(R.array.lista_prioridades));
        ArrayAdapter<String> adapterPrioridade = new ArrayAdapter<>(this, R.layout.molde_lista_spinner, lista_prioridades);
        adapterPrioridade.setDropDownViewResource(R.layout.molde_lista_spinner);
        this.input_spinner_prioridade.setAdapter(adapterPrioridade);
        input_spinner_prioridade.setSelection(0);


        //Buscando lista de dados para spinner no resources de string
        List<String> lista_categorias = Arrays.asList(getResources().getStringArray(R.array.lista_categorias));
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(this, R.layout.molde_lista_spinner, lista_categorias);
        adapterCategorias.setDropDownViewResource(R.layout.molde_lista_spinner);
        input_spinner_categorias.setAdapter(adapterCategorias);
        input_spinner_categorias.setSelection(0);//Item padrão
    }


    //Evento de click no btn menu
    private void showMenu(int ID_componente) {
        findViewById(ID_componente).setOnClickListener((e) -> {
            MenuBottom menu = new MenuBottom(Cadastro_Activity.this);
            menu.show();
        });
    }


    public void voltarHome() {
        findViewById(R.id.btn_cancelar).setOnClickListener((e) -> {
            finish();
        });
    }


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
            String categoria_produto = input_spinner_categorias.getSelectedItem().toString();
            double preco_produto = !(input_preco.getText().toString().isEmpty()) ? Double.parseDouble(input_preco.getText().toString()) : 0;


            //Pegando lista de dados para spinner no resources de string
            String[] string_array_prioridades = getResources().getStringArray(R.array.lista_prioridades);
            List<String> lista_prioridades = new ArrayList<>(Arrays.asList(string_array_prioridades));
            int indecePrioridade = lista_prioridades.indexOf(prioridade_produto);

            //Pegando lista de dados para spinner no resources de string
            String[] string_array_categoria = getResources().getStringArray(R.array.lista_categorias);
            List<String> lista_categorias = new ArrayList<>(Arrays.asList(string_array_categoria));
            int indeceCategoria = lista_categorias.indexOf(categoria_produto);


            //Validando se nome já existe
            Optional<Produto> produtoEncontrado = conexao_db.produtoRepository().findByName(nome_produto);
            if (produtoEncontrado.isPresent()) {
                throw new DuplicateNameException(getString(R.string.mensagem_toast_nome_repetido));
            }


            //Criando modelo
            Produto novoProduto = new Produto(
                    nome_produto, quantidade_produto,
                    indecePrioridade,
                    indeceCategoria,
                    preco_produto,
                    false
            );


            //Salvando
            conexao_db.produtoRepository().save(novoProduto);

            //Menssagem de sucesso e retornar para home
            Toast.makeText(Cadastro_Activity.this, getString(R.string.mensagem_toast_sucesso_salvar), Toast.LENGTH_LONG).show();
            finish();
        } catch (DuplicateNameException | BadRequestException erro) {
            Toast.makeText(Cadastro_Activity.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            conexao_db.close();
        }
    }


}