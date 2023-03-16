package com.exercicios.jogobixo.core.dominio;

import java.time.LocalDate;
import java.util.*;

public class ResultadoDia {
    private LocalDate sorteadoEm;
    private HashMap<HorarioJogos, List<ResultadoHorario>> horarios = new LinkedHashMap<>();

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

    public void inserirResultadoHorario(HorarioJogos horario, String resultado) {
        if (this.horarios.containsKey(horario)) {
            var resutadoHorarioAtual = new ArrayList<>((this.horarios.get(horario)).stream().toList());
            resutadoHorarioAtual.add(new ResultadoHorario(resutadoHorarioAtual.size() + 1, resultado));
            this.horarios.put(horario, resutadoHorarioAtual);
        } else {
            this.horarios.put(horario, List.of(new ResultadoHorario(1, resultado)));
        }
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
