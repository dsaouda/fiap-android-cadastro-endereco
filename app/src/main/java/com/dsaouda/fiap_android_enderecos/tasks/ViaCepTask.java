package com.dsaouda.fiap_android_enderecos.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.dsaouda.fiap_android_enderecos.EnderecoActivity;
import com.dsaouda.fiap_android_enderecos.dto.EnderecoDto;
import com.dsaouda.fiap_android_enderecos.model.Endereco;
import com.dsaouda.fiap_android_enderecos.services.ViaCepService;

public class ViaCepTask extends AsyncTask<String, Void, EnderecoDto> {

    private ProgressDialog pgLoading;
    private EnderecoActivity enderecoActivity;

    public ViaCepTask(EnderecoActivity enderecoActivity) {
        this.enderecoActivity = enderecoActivity;
        pgLoading = new ProgressDialog(enderecoActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pgLoading.setMessage("Buscando endereço ...");
        pgLoading.show();
    }

    @Override
    protected EnderecoDto doInBackground(String... params) {
        String cep = params[0];
        final EnderecoDto endereco = new ViaCepService().consultar(cep);

        return endereco;
    }

    @Override
    protected void onPostExecute(EnderecoDto endereco) {
        enderecoActivity.preencherCampos(endereco);
        pgLoading.hide();
    }
}
