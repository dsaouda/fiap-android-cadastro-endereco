package com.dsaouda.fiap_android_enderecos;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dsaouda.fiap_android_enderecos.dto.EnderecoDto;
import com.dsaouda.fiap_android_enderecos.model.Endereco;
import com.dsaouda.fiap_android_enderecos.tasks.ViaCepTask;
import com.dsaouda.fiap_android_enderecos.validator.ErrorValidator;

import static com.dsaouda.fiap_android_enderecos.util.TextInputLayoutUtils.valor;

public class EnderecoActivity extends AppCompatActivity {

    private TextInputLayout tilTitulo;
    private TextInputLayout tilCep;
    private TextInputLayout tilLogradouro;
    private TextInputLayout tilNumero;
    private TextInputLayout tilComplemento;
    private TextInputLayout tilBairro;
    private TextInputLayout tilCidade;
    private TextInputLayout tilUF;
    private ErrorValidator errors = new ErrorValidator();
    private Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);

        tilTitulo = (TextInputLayout) findViewById(R.id.tilTitulo);
        tilCep = (TextInputLayout) findViewById(R.id.tilCep);
        tilCep.getEditText().setOnFocusChangeListener(tilCepFocusAction());

        tilLogradouro = (TextInputLayout) findViewById(R.id.tilLogradouro);
        tilNumero = (TextInputLayout) findViewById(R.id.tilNumero);
        tilComplemento = (TextInputLayout) findViewById(R.id.tilComplemento);
        tilBairro = (TextInputLayout) findViewById(R.id.tilBairro);
        tilCidade = (TextInputLayout) findViewById(R.id.tilCidade);
        tilUF = (TextInputLayout) findViewById(R.id.tilUF);

        carregarEndereco();
    }

    private View.OnFocusChangeListener tilCepFocusAction() {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String cep = valor(tilCep);
                errors.remove(R.id.tilCep);
                if (cep.length() < 8) {
                    errors.put(R.id.tilCep, "CEP precisa ter 8 caracteres");
                    return ;
                }

                if (hasFocus == false) {
                    new ViaCepTask(EnderecoActivity.this).execute(cep);
                }
            }
        };
    }

    private void validator() {

        errors.remove(R.id.tilTitulo);
        errors.remove(R.id.tilCep);
        errors.remove(R.id.tilLogradouro);
        errors.remove(R.id.tilBairro);
        errors.remove(R.id.tilCidade);
        errors.remove(R.id.tilUF);

        if (valor(tilTitulo).length() < 3) {
            errors.put(R.id.tilTitulo, "Título precisa ter pelo menos 3 caracteres");
        }

        if (valor(tilCep).length() != 8) {
            errors.put(R.id.tilCep, "CEP precisa ter 8 caracteres");
        }

        if (valor(tilLogradouro).length() < 3) {
            errors.put(R.id.tilLogradouro, "Logradouro precisa ter 3 caracteres");
        }

        if (valor(tilNumero).length() < 1) {
            errors.put(R.id.tilNumero, "Número precisa ser preenchido");
        }

        if (valor(tilBairro).length() < 3) {
            errors.put(R.id.tilBairro, "Bairro precisa ter 3 caracteres");
        }

        if (valor(tilCidade).length() < 3) {
            errors.put(R.id.tilCidade, "Cidade precisa ter 3 caracteres");
        }

        if (valor(tilUF).length() != 2) {
            errors.put(R.id.tilUF, "UF não é válido");
        }
    }

    public void salvar(View v) {
        validator();

        if (errors.hasError()) {
            errors.show(this);
            return ;
        }

        endereco.setTitulo(valor(tilTitulo));
        endereco.setCep(valor(tilCep));
        endereco.setLogradouro(valor(tilLogradouro));
        endereco.setNumero(Integer.parseInt(valor(tilNumero)));
        endereco.setComplemento(valor(tilComplemento));
        endereco.setBairro(valor(tilBairro));
        endereco.setUf(valor(tilUF));
        endereco.setCidade(valor(tilCidade));
        endereco.save();

        voltarTelaAnterior();
    }

    public void preencherCampos(EnderecoDto endereco) {
        tilLogradouro.getEditText().setText(endereco.getLogradouro());
        tilComplemento.getEditText().setText(endereco.getComplemento());
        tilBairro.getEditText().setText(endereco.getBairro());
        tilCidade.getEditText().setText(endereco.getCidade());
        tilUF.getEditText().setText(endereco.getUf());
    }

    public void carregarEndereco() {
        final Bundle extras = getIntent().getExtras();
        Long enderecoId = (extras != null) ? extras.getLong("endereco") : null;

        if (enderecoId == null) {
            endereco = new Endereco();
        } else {
            endereco = Endereco.load(Endereco.class, enderecoId);

            valor(tilTitulo, endereco.getTitulo());
            valor(tilNumero, String.valueOf(endereco.getNumero()));
            valor(tilCep, endereco.getCep());
            valor(tilLogradouro, endereco.getLogradouro());
            tilLogradouro.getEditText().requestFocus();

            valor(tilBairro, endereco.getBairro());
            valor(tilComplemento, endereco.getComplemento());
            valor(tilCidade, endereco.getCidade());
            valor(tilUF, endereco.getUf());
        }
    }

    private void voltarTelaAnterior() {
        Intent intentMessage = new Intent();
        setResult(201, intentMessage);
        finish();
    }
}
