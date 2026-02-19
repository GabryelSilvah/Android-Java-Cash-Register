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
        findViewById(ID_componente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBottom menu = new MenuBottom(Sobre_activity.this);
                menu.show();
            }
        });
    }


    private void voltarConfig(){
        findViewById(R.id.btn_voltar_sobre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sobre_activity.this, Config_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}