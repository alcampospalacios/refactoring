package com.kreitek.refactor.bien;


import com.kreitek.refactor.bien.domain.Documento;
import com.kreitek.refactor.bien.domain.DocumentoFactory;
import com.kreitek.refactor.mal.TIPODNI;

import java.util.Arrays;
import java.util.List;

class Main
{
    static class CasoPrueba {
        TIPODNI tipo;
        String numero;
        String descripcion;

        public CasoPrueba(TIPODNI tipo, String numero, String descripcion) {
            this.tipo = tipo;
            this.numero = numero;
            this.descripcion = descripcion;
        }
    }

    public static void main(String args[])
    {
        System.out.println("=====================");
        System.out.println("Vamos a refactorizar nueva version!");
        System.out.println("=====================");

        List<CasoPrueba> documentos = Arrays.asList(
                new CasoPrueba(TIPODNI.DNI, "11111111H", "DNI correcto"),
                new CasoPrueba(TIPODNI.DNI, "24324356A", "DNI incorrecto"),
                new CasoPrueba(TIPODNI.NIE, "X0932707B", "NIE correcto"),
                new CasoPrueba(TIPODNI.NIE, "Z2691139Z", "NIE incorrecto"),
                new CasoPrueba(TIPODNI.CIF, "W9696294I", "CIF correcto"),
                new CasoPrueba(TIPODNI.CIF, "W9696294A", "CIF incorrecto")
        );

        validarDocumentos(documentos);
    }


    private static void validarDocumentos(List<CasoPrueba> documentos) {
        for (CasoPrueba caso : documentos) {
            validarYMostrar(caso);
        }
    }

    private static void validarYMostrar(CasoPrueba caso) {
        Documento documento = DocumentoFactory.crearDocumento(caso.tipo, caso.numero, null);
        System.out.println(String.format("%s %s es: %s",
                caso.tipo,
                caso.numero,
                documento.esValido()));
    }
}

