package prominence.divisao7.cash_register.common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import prominence.divisao7.cash_register.ui.home.HomeActivity;
import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.ui.produtos.Cadastro_Activity;
import prominence.divisao7.cash_register.ui.configuracoes.Config_activity;
import prominence.divisao7.cash_register.ui.produtos.Valores_activity;

public class MenuBottom {

    private Context contexto;
    private Dialog dialogMenu;

    
    public MenuBottom(Context contexto) {
        this.contexto = contexto;
        this.dialogMenu = new Dialog(contexto);
    }


    public void show() {
        dialogMenu.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMenu.setContentView(R.layout.menu_bottom);
        dialogMenu.show();
        dialogMenu.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialogMenu.getWindow().getAttributes().windowAnimations
        dialogMenu.getWindow().setGravity(Gravity.BOTTOM);
        chamarHome();
        chamarValores();
        chamarCadastroItens();
        chamarConfig();
    }


    //Voltar par atela principaç
    private void chamarHome() {
        dialogMenu.findViewById(R.id.btn_inicio_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                contexto.startActivity(intent);
            }
        });
    }


    //Chamando tela de valores
    private void chamarValores() {
        dialogMenu.findViewById(R.id.btn_valores_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, Valores_activity.class);
                contexto.startActivity(intent);
            }
        });
    }


    //Chamando tela de cadastro de itens
    private void chamarCadastroItens() {
        dialogMenu.findViewById(R.id.btn_cadastro_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, Cadastro_Activity.class);
                contexto.startActivity(intent);
            }
        });
    }


    //Chamando tela de configurações
    private void chamarConfig() {
        dialogMenu.findViewById(R.id.btn_config_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, Config_activity.class);
                contexto.startActivity(intent);
            }
        });
    }


}
