package prominence.divisao7.cash_register.ui.configuracoes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.common.MenuBottom;

public class Config_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        irParaIdiomas();
        irParaTermos();
        irParaSobre();

        showMenu(R.id.btn_menu);

    }

    //Evento de click no btn menu
    private void showMenu(int ID_componente) {
        findViewById(ID_componente).setOnClickListener((e)-> {
                MenuBottom menu = new MenuBottom(Config_activity.this);
                menu.show();
        });
    }


    private void irParaIdiomas() {
        findViewById(R.id.config_idioma).setOnClickListener((e)-> {
                Intent intent = new Intent(Config_activity.this, Idioma_activity.class);
                startActivity(intent);
        });
    }


    private void irParaTermos() {
        findViewById(R.id.config_termos_and_politicas).setOnClickListener((e)-> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://cash-register-termos.blogspot.com/"));
                startActivity(intent);
        });
    }


    private void irParaSobre() {
        findViewById(R.id.config_sobre).setOnClickListener((e)-> {
                Intent intent = new Intent(Config_activity.this, Sobre_activity.class);
                startActivity(intent);
        });
    }

}