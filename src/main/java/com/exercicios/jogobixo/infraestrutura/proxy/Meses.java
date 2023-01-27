package com.exercicios.jogobixo.infraestrutura.proxy;

import java.time.Month;

public enum Meses {
    JANEIRO("Janeiro"),
    FEVEREIRO("Fevereiro"),
    MARCO("Março"),
    ABRIL("Abril"),
    MAIO("Maio"),
    JUNHO("Junho"),
    JULHO("Julho"),
    AGOSTO("Agosto"),
    SETEMBRO("Setembro"),
    OUTUBRO("Outubro"),
    NOVEMBRO("Novembro"),
    DEZEMBRO("Dezembro");

    private String valor;

    private Meses(String valor) {
        this.valor = valor;
    }



    public static Month converterParaMonth(Meses mes) {
        return switch (mes) {
            case JANEIRO -> Month.JANUARY;
            case FEVEREIRO -> Month.FEBRUARY;
            case MARCO -> Month.MARCH;
            case ABRIL -> Month.APRIL;
            case MAIO -> Month.MAY;
            case JUNHO -> Month.JUNE;
            case JULHO -> Month.JULY;
            case AGOSTO -> Month.AUGUST;
            case SETEMBRO -> Month.SEPTEMBER;
            case OUTUBRO -> Month.OCTOBER;
            case NOVEMBRO -> Month.NOVEMBER;
            case DEZEMBRO -> Month.DECEMBER;
        };
    }

    @Override
    public String toString() {
        return valor;
    }
}
