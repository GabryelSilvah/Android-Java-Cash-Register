package prominence.divisao7.cash_register.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.common.Translation_idioma;
import prominence.divisao7.cash_register.dao.Conexao;


public class BoasVindas_activity extends AppCompatActivity {

    private Spinner input_spinner_idioma;
    private ImageView btn_img_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.boas_vindas);
        inicializar();


    }

    private void inicializar() {

        this.input_spinner_idioma = findViewById(R.id.input_spinner_idioma_boasVindas);
        this.btn_img_next = findViewById(R.id.btn_img_next);


        //Pegando lista de dados para spinner no resources de string
        String[] string_array_idiomas = getResources().getStringArray(R.array.lista_idiomas);
        List<String> lista_idiomas = Arrays.asList(string_array_idiomas);

        ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(this, R.layout.molde_lista_spinner, lista_idiomas);
        adapterIdiomas.setDropDownViewResource(R.layout.molde_lista_spinner);


        //Adicionando lista de filtros no spinner
        this.input_spinner_idioma.setAdapter(adapterIdiomas);

        switch (Locale.getDefault().getLanguage()) {
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


        this.btn_img_next.setOnClickListener((e) -> {

            int positionSelected = input_spinner_idioma.getSelectedItemPosition();

            switch (positionSelected) {
                case 0:
                    Translation_idioma.setLocale("pt", BoasVindas_activity.this);
                    break;
                case 1:
                    Translation_idioma.setLocale("en", BoasVindas_activity.this);
                    break;
                case 2:
                    Translation_idioma.setLocale("es", BoasVindas_activity.this);
                    break;

            }


            Conexao conexao_db = Conexao.getInstancia(BoasVindas_activity.this);
            conexao_db.idiomaRepository().updateStatusAlteracao(false);
            Intent intent = new Intent(BoasVindas_activity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        });


    }
}