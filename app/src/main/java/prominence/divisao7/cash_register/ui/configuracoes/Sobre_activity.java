package prominence.divisao7.cash_register.ui.configuracoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.common.MenuBottom;

public class Sobre_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobre);

        showMenu(R.id.btn_menu);
        voltarConfig();


    }

    //Evento de click no btn menu
    private void showMenu(int ID_componente) {
        findViewById(ID_componente).setOnClickListener((e) -> {
            MenuBottom menu = new MenuBottom(Sobre_activity.this);
            menu.show();
        });
    }


    private void voltarConfig() {
        findViewById(R.id.btn_voltar_sobre).setOnClickListener((e) -> {
            finish();
        });
    }

}