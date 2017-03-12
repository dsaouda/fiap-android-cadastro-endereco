package com.dsaouda.fiap_android_enderecos.services;

import com.dsaouda.fiap_android_enderecos.dto.EnderecoDto;
import com.dsaouda.fiap_android_enderecos.exceptions.ViaCepException;
import com.dsaouda.fiap_android_enderecos.model.Endereco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Modifier;

public class ViaCepService {

    private static final String uri = "https://viacep.com.br/ws/${cep}/json/";

    public EnderecoDto consultar(String cep) {

        try {
            final Connection connect = Jsoup.connect(uri.replace("${cep}", cep))
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .validateTLSCertificates(false);

            final Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .serializeNulls()
                    .create();

            String json = connect.get().body().text();
            return gson.fromJson(json, EnderecoDto.class);
        } catch (IOException e) {
            throw new ViaCepException(e);
        }
    }

}
