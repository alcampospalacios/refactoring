package com.kreitek.refactor.bien.domain;

import com.kreitek.refactor.mal.TIPODNI;

import java.util.Date;

public class DocumentoFactory {
    public static Documento crearDocumento(TIPODNI tipo, String numero, Date fechaValidez) {
        return switch (tipo) {
            case DNI -> new DocumentoDNI(numero, fechaValidez);
            case CIF -> new DocumentoCIF(numero, fechaValidez);
            case NIE -> new DocumentoNIE(numero, fechaValidez);
            default -> throw new IllegalArgumentException("Tipo de documento no soportado: " + tipo);
        };
    }
}
