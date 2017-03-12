package com.dsaouda.fiap_android_enderecos.repository;

import com.activeandroid.query.Select;
import com.dsaouda.fiap_android_enderecos.model.Endereco;

import java.util.List;

public class EnderecoRepository {

    public Endereco buscarPorCep(String cep) {
        return new Select().from(Endereco.class).where("cep = :cep", cep).executeSingle();
    }

    public List<Endereco> buscarTodos() {
        return new Select().from(Endereco.class).execute();
    }
}
