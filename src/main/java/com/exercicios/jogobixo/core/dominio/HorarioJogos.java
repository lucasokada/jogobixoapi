package com.exercicios.jogobixo.core.dominio;

import java.util.Set;

public enum HorarioJogos {
    PTM,
    PT,
    PTV,
    FED,
    COR;

    public static Set<HorarioJogos> recuperaTodosValores() {
        return Set.of(HorarioJogos.values());
    }
}
