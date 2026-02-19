package prominence.divisao7.cash_register.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.model.Produto;


public class HolderProduto extends RecyclerView.ViewHolder {

    public int id_produto;
    public TextView nome_produto;
    public TextView quantidade;
    public TextView prioridade;
    public Produto produto;
    public LinearLayout simbolo_confirm_item;
    public AppCompatButton btn_editar;
    public AppCompatButton btn_excluir;
    public CardView card_produto_home;


    //Construtor da classe
    public HolderProduto(@NonNull View itemView) {
        super(itemView);
        inicializar();
    }


    //Inicializando variáveis
    private void inicializar() {
        nome_produto = itemView.findViewById(R.id.nome_produto);
        quantidade = itemView.findViewById(R.id.quantidade);
        prioridade = itemView.findViewById(R.id.prioridade);
        simbolo_confirm_item = itemView.findViewById(R.id.simbolo_confirm_item);
        btn_editar = itemView.findViewById(R.id.btn_editar);
        btn_excluir = itemView.findViewById(R.id.btn_excluir);
        card_produto_home = itemView.findViewById(R.id.card_produto_home);
    }

}
