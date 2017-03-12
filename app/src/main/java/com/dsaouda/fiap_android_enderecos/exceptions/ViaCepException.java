package com.dsaouda.fiap_android_enderecos.exceptions;

import java.io.IOException;

public class ViaCepException extends RuntimeException {
    public ViaCepException(IOException e) {
        super(e);
    }
}
