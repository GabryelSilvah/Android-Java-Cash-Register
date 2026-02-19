package prominence.divisao7.cash_register.common;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

public class Recurso {

    public static void exibirMensagemNotFound(ViewGroup layout, Context contexto, String texto_mensagem) {
        //Removendo o TextView adicionado quando a lista está vazia para não duplicar o TextView
        layout.removeAllViews();


        //Adicionando nova TextView com a mensagem de nenhum item encontrado
        TextView mensagem = new TextView(contexto);
        mensagem.setText(texto_mensagem);
        layout.addView(mensagem);
        ViewGroup.MarginLayoutParams teste = (ViewGroup.MarginLayoutParams) mensagem.getLayoutParams();
        teste.setMarginStart(20);
    }
}
