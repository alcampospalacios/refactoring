package com.kreitek.refactor.bien.domain;

import java.util.Date;

 class DocumentoDNI extends Documento {
    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    public DocumentoDNI(String numero, Date fechaValidez) {
        super(numero, fechaValidez);
    }

    @Override
    public boolean esValido() {
        if (numero == null || numero.length() != 9) {
            return false;
        }

        String numeroLimpio = numero.trim().replaceAll(" ", "");
        String parteNumerica = numeroLimpio.substring(0, 8);
        char letraDocumento = numeroLimpio.charAt(8);

        if (!esNumerico(parteNumerica)) {
            return false;
        }

        int valorNumerico = Integer.parseInt(parteNumerica);
        int indiceLetra = valorNumerico % 23;
        char letraEsperada = LETRAS_DNI.charAt(indiceLetra);

        return letraEsperada == letraDocumento;
    }
}