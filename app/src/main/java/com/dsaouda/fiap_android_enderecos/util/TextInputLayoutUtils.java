package com.dsaouda.fiap_android_enderecos.util;

import android.support.design.widget.TextInputLayout;

final public class TextInputLayoutUtils {

    private TextInputLayoutUtils() {}

    public static String valor(TextInputLayout til) {
        return til.getEditText().getText().toString();
    }

    public static void valor(TextInputLayout til, String valor) {
        til.getEditText().setText(valor);
    }

}
