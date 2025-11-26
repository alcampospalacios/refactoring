package com.kreitek.refactor.bien.domain;

import java.util.Date;

public abstract class Documento {
    protected String numero;
    protected Date fechaValidez;

    public Documento(String numero, Date fechaValidez) {
        this.numero = numero;
        this.fechaValidez = fechaValidez;
    }

    public abstract boolean esValido();

    protected static boolean esNumerico(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
