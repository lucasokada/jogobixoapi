package com.exercicios.jogobixo.core.dominio;

import java.util.List;
import java.util.Set;

public enum HorarioJogos {
    PTM (1),
    PT (2),
    PTV (3),
    FED (4),
    COR (5);

    private int ordem;

    HorarioJogos(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return this.ordem;
    }

    public static Set<HorarioJogos> recuperaTodosValores() {
        return Set.of(HorarioJogos.values());
    }
    public static List<HorarioJogos> recuperaListaHorarios() {return List.of(HorarioJogos.values());}
}
