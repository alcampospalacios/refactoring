package com.kreitek.refactor.bien.domain;

import java.util.Date;

class DocumentoNIE extends Documento {
    private static final char[] LETRAS_ASIGNACION = {
            'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X',
            'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'
    };

    public DocumentoNIE(String numero, Date fechaValidez) {
        super(numero, fechaValidez);
    }

    @Override
    public boolean esValido() {
        if (numero == null || numero.length() != 9) {
            return false;
        }

        String nieNormalizado = normalizarNIE(numero);

        if (nieNormalizado == null) {
            return false;
        }

        char letraDocumento = Character.toUpperCase(numero.charAt(8));
        int parteNumerica = Integer.parseInt(nieNormalizado.substring(0, 8));
        int indice = parteNumerica % 23;

        return letraDocumento == LETRAS_ASIGNACION[indice];
    }

    private String normalizarNIE(String nie) {
        char primerCaracter = nie.substring(0, 1).toUpperCase().charAt(0);

        if (primerCaracter != 'X' && primerCaracter != 'Y' && primerCaracter != 'Z') {
            return null;
        }

        if (!Character.isLetter(nie.charAt(8))) {
            return null;
        }

        // Validar que los caracteres del medio sean números
        for (int i = 1; i < 8; i++) {
            if (!Character.isDigit(nie.charAt(i))) {
                return null;
            }
        }

        // Reemplazar la primera letra por el número correspondiente
        String prefijo;
        switch (primerCaracter) {
            case 'X': prefijo = "0"; break;
            case 'Y': prefijo = "1"; break;
            case 'Z': prefijo = "2"; break;
            default: return null;
        }

        return prefijo + nie.substring(1);
    }
}
