package prominence.divisao7.cash_register.ui.configuracoes;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.common.MenuBottom;
import prominence.divisao7.cash_register.common.Translation_idioma;
import prominence.divisao7.cash_register.dao.Conexao;
import prominence.divisao7.cash_register.model.Idioma;

public class Idioma_activity extends AppCompatActivity {

    private Spinner input_spinner_idioma;
    private Conexao conexao_db;
    private AppCompatButton btn_alterar_idioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idioma);


        inicializar();
        showMenu(R.id.btn_menu);
        escolherIdioma();
        conexao_db.close();

    }


    private void inicializar() {

        this.input_spinner_idioma = findViewById(R.id.input_spinner_idioma);
        this.conexao_db = Conexao.getInstancia(this);
        this.btn_alterar_idioma = findViewById(R.id.btn_alterar_idioma);


        //Pegando lista de dados para spinner no resources de string
        //Adicionando lista de filtros no spinner
        String[] string_array_prioridades = getResources().getStringArray(R.array.lista_idiomas);
        List<String> lista_idiomas = Arrays.asList(string_array_prioridades);

        ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(this, R.layout.molde_lista_spinner, lista_idiomas);
        adapterIdiomas.setDropDownViewResource(R.layout.molde_lista_spinner);
        input_spinner_idioma.setAdapter(adapterIdiomas);


        //Validando se foi encontrado idioma salvo, setando no spinner idioma salvo
        Optional<Idioma> idiomaEncontrado = this.conexao_db.idiomaRepository().findLanguage();
        if (idiomaEncontrado.isPresent()) {
            switch (idiomaEncontrado.get().getNomeISO_idioma()) {
                case "pt":
                    this.input_spinner_idioma.setSelection(0);
                    break;
                case "en":
                    this.input_spinner_idioma.setSelection(1);
                    break;
                case "es":
                    this.input_spinner_idioma.setSelection(2);
                    break;
                default:
                    this.input_spinner_idioma.setSelection(1);
                    break;
            }
        }

        conexao_db.close();
    }


    //Evento de click no btn menu
    private void showMenu(int ID_componente) {
        findViewById(ID_componente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBottom menu = new MenuBottom(Idioma_activity.this);
                menu.show();
            }
        });
    }


    private void escolherIdioma() {
        this.btn_alterar_idioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (input_spinner_idioma.getSelectedItemPosition()) {
                    case 0:
                        Translation_idioma.setLocale("pt", Idioma_activity.this);;
                        break;
                    case 1:
                        Translation_idioma.setLocale("en", Idioma_activity.this);
                        break;
                    case 2:
                        Translation_idioma.setLocale("es", Idioma_activity.this);
                        break;
                    default:
                        break;
                }
            }
        });
    }


}