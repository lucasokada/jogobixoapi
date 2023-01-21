package com.exercicios.jogobixo.core.dominio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultadoDia {
    private LocalDate sorteadoEm;
    private HashMap<HorarioJogos, List<ResultadoHorario>> horarios = new HashMap<>();

    public HashMap<HorarioJogos, List<ResultadoHorario>> getHorarios() {
        return horarios;
    }

    public LocalDate getSorteadoEm() {
        return sorteadoEm;
    }

    public void inserirResultadoHorario(HorarioJogos horario, List<String> resultados) {
        List<ResultadoHorario> resultadosNoHorario = resultados.stream()
                .map(resultado -> new ResultadoHorario(resultados.indexOf(resultado) + 1, resultado))
                .toList();

        this.horarios.put(horario, resultadosNoHorario);
    }


    public Set<HorarioJogos> recuperarHorariosPendentes() {
        Set<HorarioJogos> todosHorarios = new HashSet<>(HorarioJogos.recuperaTodosValores());
        todosHorarios.removeAll(this.horarios.keySet());

        return todosHorarios;
    }

    public ResultadoDia(LocalDate sorteadoEm) {
        this.sorteadoEm = sorteadoEm;
    }
}
