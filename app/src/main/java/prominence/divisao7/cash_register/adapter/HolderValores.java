package prominence.divisao7.cash_register.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import prominence.divisao7.cash_register.R;

public class HolderValores extends RecyclerView.ViewHolder {

    private Context contexto;
    public TextView nome_produto_valores;
    public TextView quantidade_produto_valores;
    public TextView preco_unidade_produto_valores;
    public TextView preco_total_produto_valores;

    public HolderValores(@NonNull View itemView, Context contexto) {
        super(itemView);
        this.contexto = contexto;

        inicializar(itemView);
    }


    public void inicializar(View itemView){
        this.nome_produto_valores = itemView.findViewById(R.id.nome_produto_valores);
        this.quantidade_produto_valores = itemView.findViewById(R.id.quantidade_produto_valores);
        this.preco_total_produto_valores = itemView.findViewById(R.id.preco_total_produto_valores);
        this.preco_unidade_produto_valores = itemView.findViewById(R.id.preco_unidade_produto_valores);
    }





}
