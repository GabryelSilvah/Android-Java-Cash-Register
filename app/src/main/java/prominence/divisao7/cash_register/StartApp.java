package prominence.divisao7.cash_register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Optional;

import prominence.divisao7.cash_register.dao.Conexao;
import prominence.divisao7.cash_register.model.Idioma;
import prominence.divisao7.cash_register.ui.home.BoasVindas_activity;
import prominence.divisao7.cash_register.ui.home.HomeActivity;

public class StartApp extends AppCompatActivity {
    private Conexao conexao_db;
    private String idiomaPadraoDispositivo;

    //Class de ponto de entrada, redireciona para tela de boas vindas ou home
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).post(this::validarPrimeiroAcesso);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        conexao_db.close();
    }


    private void inicializar() {
        this.conexao_db = Conexao.getInstancia(this);
        this.idiomaPadraoDispositivo = Locale.getDefault().getLanguage();
    }



    private void validarPrimeiroAcesso() {

        //Buscando idioma do app salvo
        Optional<Idioma> idiomaEncontrado = conexao_db.idiomaRepository().findLanguage();

        //Validando se existe idioma salvo
        if (idiomaEncontrado.isEmpty()) {
            setarIdiomaPadrao("en");

            //Redirecionando para tela de boas vindas
            Intent intent = new Intent(StartApp.this, BoasVindas_activity.class);
            startActivity(intent);
            finish();
            return;
        }


        //Redirecionando para tela home
        Intent intent = new Intent(StartApp.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void setarIdiomaPadrao(String idiomaInicial) {
        //verificando se o padrão do dispositivo é diferente dos permitidos
        if (!(idiomaPadraoDispositivo.equals("pt")) && !(idiomaPadraoDispositivo.equals("en")) && !(idiomaPadraoDispositivo.equals("es"))) {
            conexao_db.idiomaRepository().save(new Idioma(idiomaInicial, true));
            return;
        }

        conexao_db.idiomaRepository().save(new Idioma(idiomaPadraoDispositivo, true));

    }

}

