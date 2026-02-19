package prominence.divisao7.cash_register.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.model.Produto;

public class AdapterValores extends RecyclerView.Adapter<HolderValores> {

    private List<Produto> lista_valores;

    private Context contexto;

    public AdapterValores(List<Produto> lista, Context contexto) {
        this.lista_valores = lista;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public HolderValores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_valores, parent, false);
        return new HolderValores(view, contexto);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderValores holder, int position) {
        Produto produto = lista_valores.get(position);

        NumberFormat decimal = new DecimalFormat("0.00");
        String nome = contexto.getString(R.string.label_nome) + " " + produto.getNome_produto();
        String quantidade = contexto.getString(R.string.label_quantidade) + " " + String.valueOf(produto.getQuantidade_produto() + " Un.");
        String preco_unidade = contexto.getString(R.string.valores_preco_uni) + " " + String.valueOf(decimal.format(produto.getPreco_produto()));
        String preco_total = contexto.getString(R.string.valores_preco_total) + " " + String.valueOf(decimal.format(produto.getPreco_produto() * produto.getQuantidade_produto()));


        holder.nome_produto_valores.setText(nome);
        holder.quantidade_produto_valores.setText(quantidade);
        holder.preco_unidade_produto_valores.setText(preco_unidade);
        holder.preco_total_produto_valores.setText(preco_total);
    }

    @Override
    public int getItemCount() {
        return lista_valores.size();
    }
}
