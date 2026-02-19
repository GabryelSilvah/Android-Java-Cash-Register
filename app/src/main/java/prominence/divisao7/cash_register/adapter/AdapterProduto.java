package prominence.divisao7.cash_register.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import prominence.divisao7.cash_register.R;
import prominence.divisao7.cash_register.dao.Conexao;
import prominence.divisao7.cash_register.model.Produto;
import prominence.divisao7.cash_register.ui.produtos.Detalhes_activity;
import prominence.divisao7.cash_register.ui.produtos.EditarActivity;


public class AdapterProduto extends RecyclerView.Adapter<HolderProduto> {

    private List<Produto> lista_produtos;
    private Context contexto;
    private View viewCard_produto;
    private View viewCard_ads;
    private int positicao_item;
    private NetworkInfo networkInfo;

    public AdapterProduto(List<Produto> lista, Context contexto) {
        this.lista_produtos = lista;
        this.contexto = contexto;
    }


    @NonNull
    @Override
    public HolderProduto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewCard_produto = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_produto, parent, false);
        return new HolderProduto(viewCard_produto);
    }



    @Override
    public int getItemCount() {
        return lista_produtos.size();
    }


    @Override
    public void onBindViewHolder(HolderProduto holder, @SuppressLint("RecyclerView") int position) {

            //Pegando lista de dados para spinner no resources de string
            String[] string_array_prioridades = contexto.getResources().getStringArray(R.array.lista_prioridades);
            List<String> lista_prioridades = new ArrayList<>(Arrays.asList(string_array_prioridades));

            Produto item = lista_produtos.get(position);
            //Separando dados e adicionando rótulo/label para identificar
            String nome = contexto.getString(R.string.label_nome) + " " + item.getNome_produto();
            String quantidade = contexto.getString(R.string.label_quantidade) + " " + String.valueOf(item.getQuantidade_produto());
            String prioridade = contexto.getString(R.string.label_prioridade) + " " + lista_prioridades.get(item.getPrioridade_produto());


            //Setando dados nos componentes
            holder.id_produto = item.getId_produto();
            holder.nome_produto.setText(nome);
            holder.quantidade.setText(quantidade);
            holder.prioridade.setText(prioridade);
            holder.produto = lista_produtos.get(position);


            //Evento de click para editar informações do item
            holder.btn_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editar_produto(item);
                }
            });


            //Evento de click para excluir item
            holder.btn_excluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    excluir_produto(item.getId_produto(), position);
                }
            });


            //Evento de click para exibir detalhes do item
            holder.card_produto_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exibir_detalhes_produto(item);
                }
            });


            //Adicionando bolinha para sinalizar que o check/verificado/concluído está ativado
            if (lista_produtos.get(position).getCheck_produto()) {
                holder.simbolo_confirm_item.setBackground(contexto.getDrawable(R.drawable.circulo_check_shape));
            }

            //Se a prioridade for igual média então a cor é preta
            if (item.getPrioridade_produto() == 0) {
                holder.prioridade.setTextColor(Color.parseColor("#000000"));
            }

            //Se a prioridade for igual média então a cor é laranja
            if (item.getPrioridade_produto() == 1) {
                holder.prioridade.setTextColor(Color.parseColor("#ff6905"));
            }


            //Se a prioridade for igual alta então a cor é vermelha
            if (item.getPrioridade_produto() == 2) {
                holder.prioridade.setTextColor(Color.parseColor("#ff082d"));
            }
    }



    //Alterar as informações do produto
    private void editar_produto(Produto produto) {

        Intent intent = new Intent(contexto.getApplicationContext(), EditarActivity.class);
        //Enviando dados do produto para editar
        intent.putExtra("id_produto", produto.getId_produto());
        intent.putExtra("nome_produto", produto.getNome_produto());
        intent.putExtra("quantidade_produto", String.valueOf(produto.getQuantidade_produto()));
        intent.putExtra("prioridade_produto", produto.getPrioridade_produto());
        intent.putExtra("categoria_produto", produto.getCategoria_produto());
        intent.putExtra("preco_produto", String.valueOf(produto.getPreco_produto()));

        contexto.startActivity(intent);
    }


    //Exibir detalhes do produto
    private void exibir_detalhes_produto(Produto produto) {

        Intent intent = new Intent(contexto.getApplicationContext(), Detalhes_activity.class);
        //Enviando dados do produto para editar
        intent.putExtra("id_produto", produto.getId_produto());
        intent.putExtra("nome_produto", produto.getNome_produto());
        intent.putExtra("quantidade_produto", String.valueOf(produto.getQuantidade_produto()));
        intent.putExtra("prioridade_produto", produto.getPrioridade_produto());
        intent.putExtra("categoria_produto", produto.getCategoria_produto());
        intent.putExtra("preco_produto", String.valueOf(produto.getPreco_produto()));
        intent.putExtra("check_produto", produto.getCheck_produto());

        contexto.startActivity(intent);
    }


    //Excluir produto cadastrado
    private void excluir_produto(int id_produto, int posicao) {

        //Alert de exclusão
        AlertDialog.Builder alertExclusao = new AlertDialog.Builder(contexto);
        alertExclusao.setTitle(contexto.getString(R.string.dialog_confirmar));
        alertExclusao.setIcon(R.drawable.icon_delete);
        alertExclusao.setMessage(contexto.getString(R.string.dialog_message));


        alertExclusao.setPositiveButton(contexto.getString(R.string.dialog_positivo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Conexao db = Conexao.getInstancia(contexto);
                db.produtoRepository().delete(id_produto);
                lista_produtos.remove(posicao);
                notifyDataSetChanged();
                //notifyItemChanged(posicao);
            }
        });


        alertExclusao.setNegativeButton(contexto.getString(R.string.dialog_negativo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(contexto, contexto.getString(R.string.dialog_message_cancelar), Toast.LENGTH_LONG).show();
            }
        });


        alertExclusao.show();
    }
}

