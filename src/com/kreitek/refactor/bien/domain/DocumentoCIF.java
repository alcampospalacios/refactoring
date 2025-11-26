package com.kreitek.refactor.bien.domain;

import java.util.Date;

class DocumentoCIF extends Documento {
    private static final String LETRAS_VALIDAS = "ABCDEFGHJKLMNPQRSUVW";
    private static final String LETRAS_CONTROL = "JABCDEFGHI";

    public DocumentoCIF(String numero, Date fechaValidez) {
        super(numero, fechaValidez);
    }

    @Override
    public boolean esValido() {
        if (numero == null) {
            return false;
        }

        String cifMayusculas = numero.toUpperCase();

        if (!validarFormato(cifMayusculas)) {
            return false;
        }

        char primerCaracter = cifMayusculas.charAt(0);
        char ultimoCaracter = cifMayusculas.charAt(cifMayusculas.length() - 1);

        TipoCaracterControl tipoControl = determinarTipoControl(primerCaracter);

        if (!validarUltimoCaracter(ultimoCaracter, tipoControl)) {
            return false;
        }

        return validarDigitoControl(cifMayusculas, ultimoCaracter, tipoControl);
    }

    private boolean validarFormato(String cif) {
        if (cif.length() != 9) {
            return false;
        }

        if (LETRAS_VALIDAS.indexOf(cif.charAt(0)) == -1) {
            return false;
        }

        return cif.matches("[ABCDEFGHJKLMNPQRSUVW][0-9]{7}[A-Z0-9]");
    }

    private TipoCaracterControl determinarTipoControl(char primerCaracter) {
        if ("PQSKW".indexOf(primerCaracter) != -1) {
            return TipoCaracterControl.LETRA;
        } else if ("ABEH".indexOf(primerCaracter) != -1) {
            return TipoCaracterControl.NUMERO;
        }
        return TipoCaracterControl.AMBOS;
    }

    private boolean validarUltimoCaracter(char ultimoCaracter, TipoCaracterControl tipo) {
        if (tipo == TipoCaracterControl.LETRA) {
            return Character.isLetter(ultimoCaracter);
        } else if (tipo == TipoCaracterControl.NUMERO) {
            return Character.isDigit(ultimoCaracter);
        }
        return true; // AMBOS
    }

    private boolean validarDigitoControl(String cif, char ultimoCaracter, TipoCaracterControl tipo) {
        String digitos = cif.substring(1, cif.length() - 1);

        int sumaPares = calcularSumaPares(digitos);
        int sumaImpares = calcularSumaImpares(digitos);
        int total = sumaPares + sumaImpares;

        int numeroControl = (10 - (total % 10)) % 10;
        char letraControl = LETRAS_CONTROL.charAt(numeroControl);

        if (tipo == TipoCaracterControl.NUMERO) {
            return numeroControl == Character.getNumericValue(ultimoCaracter);
        } else if (tipo == TipoCaracterControl.LETRA) {
            return letraControl == ultimoCaracter;
        } else {
            // AMBOS: puede ser número o letra
            int indiceLetra = LETRAS_CONTROL.indexOf(ultimoCaracter);
            if (indiceLetra >= 0) {
                return indiceLetra == numeroControl;
            }
            return numeroControl == Character.getNumericValue(ultimoCaracter);
        }
    }

    private int calcularSumaPares(String digitos) {
        int suma = 0;
        for (int i = 1; i < digitos.length(); i += 2) {
            suma += Character.getNumericValue(digitos.charAt(i));
        }
        return suma;
    }

    private int calcularSumaImpares(String digitos) {
        int suma = 0;
        for (int i = 0; i < digitos.length(); i += 2) {
            int valor = Character.getNumericValue(digitos.charAt(i)) * 2;
            if (valor > 9) {
                valor = (valor / 10) + (valor % 10);
            }
            suma += valor;
        }
        return suma;
    }

    private enum TipoCaracterControl {
        LETRA, NUMERO, AMBOS
    }
}