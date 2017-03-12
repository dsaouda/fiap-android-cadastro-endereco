package com.dsaouda.fiap_android_enderecos.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsaouda.fiap_android_enderecos.R;
import com.dsaouda.fiap_android_enderecos.model.Endereco;
import com.dsaouda.fiap_android_enderecos.view.holder.EnderecoViewHolder;

import java.util.List;

public class EnderecoListaAdapter extends RecyclerView.Adapter {

    private final List<Endereco> enderecos;
    private final Context context;

    public EnderecoListaAdapter(List<Endereco> enderecos, Context context) {
        this.enderecos = enderecos;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.endereco_item_lista, parent, false);
        return new EnderecoViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final EnderecoViewHolder holder = (EnderecoViewHolder) viewHolder;
        final Endereco endereco = enderecos.get(position);
        holder.preencher(endereco);
    }

    @Override
    public int getItemCount() {
        return enderecos.size();
    }

    public void remove(int position) {
        enderecos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}
