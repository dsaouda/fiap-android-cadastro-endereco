package com.dsaouda.fiap_android_enderecos.validator;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import com.google.gson.internal.LinkedHashTreeMap;
import java.util.Map;

public class ErrorValidator {

    private Map<Integer, String> errors = new LinkedHashTreeMap<>();

    public ErrorValidator put(Integer key, String message) {
        errors.put(key, message);
        return this;
    }

    public ErrorValidator remove(Integer key) {
        errors.remove(key);
        return this;
    }

    public boolean hasError() {
        return errors.size() > 0;
    }

    public void show(Context context) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        StringBuilder sbErros = new StringBuilder();
        for (String erro: errors.values()) {
            sbErros.append("\n" + erro);
        }
        alert.setMessage(sbErros.toString());
        alert.show();
    }

}
