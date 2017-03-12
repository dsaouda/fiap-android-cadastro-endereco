package com.dsaouda.fiap_android_enderecos.view.holder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.Model;
import com.dsaouda.fiap_android_enderecos.EnderecoActivity;
import com.dsaouda.fiap_android_enderecos.R;
import com.dsaouda.fiap_android_enderecos.model.Endereco;
import com.dsaouda.fiap_android_enderecos.view.adapter.EnderecoListaAdapter;

public class EnderecoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    private final TextView tvCepCidadeUF;
    private final TextView tvLogradouro;
    private final EnderecoListaAdapter adapter;
    private Long enderecoId;

    public EnderecoViewHolder(final View view, final EnderecoListaAdapter adapter) {
        super(view);
        this.adapter = adapter;

        view.setOnLongClickListener(this);
        tvCepCidadeUF = (TextView) view.findViewById(R.id.tvCepCidadeUF);
        tvLogradouro = (TextView) view.findViewById(R.id.tvLogradouro);
    }

    public void preencher(Endereco endereco) {
        enderecoId = endereco.getId();
        tvCepCidadeUF.setText("#" + endereco.getId() + " - " + endereco.getCep() + " / " + endereco.getCidade() + " - " + endereco.getUf());
        tvLogradouro.setText(endereco.getLogradouro());
    }


    @Override
    public boolean onLongClick(final View v) {




        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.getMenuInflater().inflate(R.menu.endereco_actions, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menuEditar:

                        final Activity context = (Activity)v.getContext();
                        final Intent intent = new Intent(context, EnderecoActivity.class);
                        intent.putExtra("endereco", enderecoId);
                        context.startActivityForResult(intent, 201);

                        break;

                    case R.id.menuDeletar:
                        Model.delete(Endereco.class, enderecoId);

                        Log.i("POSICAO", "" + EnderecoViewHolder.this.getAdapterPosition());


                        adapter.remove(EnderecoViewHolder.this.getAdapterPosition());



                        break;

                    case R.id.menuVisualizarNoMapa:

                        break;
                }

                return true;
            }
        });

        popup.show();
        return false;
    }
}
